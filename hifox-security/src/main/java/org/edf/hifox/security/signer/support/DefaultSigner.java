package org.edf.hifox.security.signer.support;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

import org.edf.hifox.core.exception.FailureException;
import org.edf.hifox.core.util.SwapAreaUtil;
import org.edf.hifox.security.constant.ErrorCodeConstant;
import org.edf.hifox.security.signer.Signer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

public class DefaultSigner implements Signer, InitializingBean {
	private String algorithm;
	private KeyStore keyStore;
	private Key key;
	
	private Resource resource;
	private String charsetName;
	private String alias;
	private String storeType;
	private String storePassword;
	private String keyPassword;
	private SecureRandom secureRandom = new SecureRandom(new byte[]{});


	@Override
	public void update(byte[] input) {
		try {
			Signature signature = SwapAreaUtil.getSignatureObject();
			if (signature == null) {
				signature = Signature.getInstance(algorithm);
				signature.initSign((PrivateKey)key, secureRandom);
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
		try {
			update(input.getBytes(charsetName));
		} catch (Exception e) {
			throw new FailureException(ErrorCodeConstant.E0001S071, new Object[]{e.getMessage()}, e);
		}
	}
	

	@Override
	public byte[] sign() {
		try {
			Signature signature = SwapAreaUtil.getSignatureObject();
			byte[] sign = signature.sign();
			return sign;
		} catch (SignatureException e) {
			throw new FailureException(ErrorCodeConstant.E0001S071, new Object[]{e.getMessage()}, e);
		}
		
	}
	
	@Override
	public String sign(String charsetName) {
		try {
			String signString  = new String(sign(), charsetName);
			return signString;
		} catch (Exception e) {
			throw new FailureException(ErrorCodeConstant.E0001S071, new Object[]{e.getMessage()}, e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		InputStream is = null;
		try {
			keyStore = KeyStore.getInstance(storeType);
			is = resource.getInputStream();
			keyStore.load(is, storePassword.toCharArray());
		} finally {
			if (is != null)
				is.close();
		}
		
		key = keyStore.getKey(alias, keyPassword.toCharArray());
		
	}

}
