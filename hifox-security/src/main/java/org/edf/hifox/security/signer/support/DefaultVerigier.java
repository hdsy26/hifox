package org.edf.hifox.security.signer.support;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;

import org.edf.hifox.core.exception.FailureException;
import org.edf.hifox.core.util.SwapAreaUtil;
import org.edf.hifox.security.constant.ErrorCodeConstant;
import org.edf.hifox.security.signer.Verifier;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

public class DefaultVerigier implements Verifier, InitializingBean {
	private String algorithm = "SHA1withDSA";
	private KeyStore keyStore;
	private Key key;
	
	private Resource resource;
	private String charsetName;
	private String alias;
	private String storeType;
	private String storePassword;
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
			throw new FailureException(ErrorCodeConstant.E0001S072, new Object[]{e.getMessage()}, e);
		}
	}
	
	@Override
	public void update(String input) {
		try {
			update(input.getBytes(charsetName));
		} catch (Exception e) {
			throw new FailureException(ErrorCodeConstant.E0001S072, new Object[]{e.getMessage()}, e);
		}
	}
	
	@Override
	public boolean verify(byte[] signValue) {
		try {
			Signature signature = SwapAreaUtil.getSignatureObject();
			boolean result = signature.verify(signValue);
			return result;
		} catch (Exception e) {
			throw new FailureException(ErrorCodeConstant.E0001S072, new Object[]{e.getMessage()}, e);
		}
	}

	@Override
	public boolean verify(String signValueString) {
		try {
			boolean result = verify(signValueString.getBytes(charsetName));
			return result;
		} catch (Exception e) {
			throw new FailureException(ErrorCodeConstant.E0001S072, new Object[]{e.getMessage()}, e);
		}
	}
	
	@Override
	public boolean verify(String input, String signValueString) {
		update(input);
		boolean result = verify(signValueString);
		return result;
	}
	

	@Override
	public void afterPropertiesSet() throws Exception {
		InputStream is = null;
		try {
			keyStore = KeyStore.getInstance(storeType);
			is = resource.getInputStream();
			keyStore.load(is, storePassword.toCharArray());
			key = keyStore.getCertificate(alias).getPublicKey();
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

	public void setSecureRandom(SecureRandom secureRandom) {
		this.secureRandom = secureRandom;
	}

}
