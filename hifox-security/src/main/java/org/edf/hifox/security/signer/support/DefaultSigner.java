package org.edf.hifox.security.signer.support;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.codec.binary.Base64;
import org.edf.hifox.core.exception.FailureException;
import org.edf.hifox.core.util.ByteArrayUtil;
import org.edf.hifox.core.util.StringUtil;
import org.edf.hifox.core.util.SwapAreaUtil;
import org.edf.hifox.security.constant.ErrorCodeConstant;
import org.edf.hifox.security.signer.Signer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

public class DefaultSigner implements Signer, InitializingBean {
	private String algorithm = "SHA1withDSA";
	private KeyStore keyStore;
	private Key key;
	
	private Resource resource;
	private String charsetName = "UTF-8";
	private String alias;
	private String storeType;
	private String storePassword;
	private String keyPassword;

	
	@Override
	public void update(byte[] input) {
		try {
			Signature signature = SwapAreaUtil.getSignatureObject();
			if (signature == null) {
				signature = Signature.getInstance(algorithm);
				signature.initSign((PrivateKey)key);
				SwapAreaUtil.setSignatureObject(signature);
			}
			
			signature.update(input);
			
		} catch (NoSuchAlgorithmException e) {
			throw new FailureException(ErrorCodeConstant.E0001S070, new Object[]{algorithm}, e);
		} catch (Exception e) {
			throw new FailureException(ErrorCodeConstant.E0001S071, new Object[]{e.getMessage()}, e);
		}
	}
	
	@Override
	public void update(String input) {
		update(StringUtil.toBytes(input, charsetName));
	}
	

	@Override
	public byte[] sign() {
		try {
			Signature signature = SwapAreaUtil.getSignatureObject();
			byte[] sign = signature.sign();
			byte[] encode = Base64.encodeBase64(sign);
			return encode;
		} catch (SignatureException e) {
			throw new FailureException(ErrorCodeConstant.E0001S071, new Object[]{e.getMessage()}, e);
		}
		
	}
	
	@Override
	public String signString() {
		String encodeString  = ByteArrayUtil.toString(sign(), charsetName);
		return encodeString;
	}
	
	@Override
	public String signString(String input) {
		update(input);
		String encodeString = signString();
		return encodeString;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		InputStream is = null;
		try {
			keyStore = KeyStore.getInstance(storeType);
			is = resource.getInputStream();
			keyStore.load(is, storePassword.toCharArray());
			key = keyStore.getKey(alias, keyPassword.toCharArray());
		} finally {
			if (is != null)
				is.close();
		}
	}

	
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public void setStorePassword(String storePassword) {
		this.storePassword = storePassword;
	}

	public void setKeyPassword(String keyPassword) {
		this.keyPassword = keyPassword;
	}

}
