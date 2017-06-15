package org.edf.hifox.security.signer.support;

import java.io.InputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import org.apache.commons.codec.binary.Base64;
import org.edf.hifox.core.exception.FailureException;
import org.edf.hifox.core.util.StringUtil;
import org.edf.hifox.core.util.SwapAreaUtil;
import org.edf.hifox.security.constant.ErrorCodeConstant;
import org.edf.hifox.security.signer.Verifier;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

public class DefaultVerigier implements Verifier, InitializingBean {
	private String algorithm = "SHA1withDSA";
	private Key key;
	
	private Resource resource;
	private String charsetName = "UTF-8";

	
	@Override
	public void update(byte[] input) {
		try {
			Signature signature = SwapAreaUtil.getSignatureObject();
			if (signature == null) {
				signature = Signature.getInstance(algorithm);
				signature.initVerify((PublicKey)key);
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
		update(StringUtil.toBytes(input, charsetName));
	}
	
	@Override
	public boolean verify(byte[] signvalue) {
		try {
			Signature signature = SwapAreaUtil.getSignatureObject();
			byte[] decode = Base64.decodeBase64(signvalue);
			boolean result = signature.verify(decode);
			return result;
		} catch (Exception e) {
			throw new FailureException(ErrorCodeConstant.E0001S072, new Object[]{e.getMessage()}, e);
		}
	}

	@Override
	public boolean verify(String signvalueString) {
		byte[] signValue = StringUtil.toBytes(signvalueString, charsetName);
		boolean result = verify(signValue);
		return result;
	}
	
	@Override
	public boolean verify(String input, String signvalueString) {
		update(input);
		boolean result = verify(signvalueString);
		return result;
	}
	

	@Override
	public void afterPropertiesSet() throws Exception {
		InputStream is = null;
		try {
			CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
			is = resource.getInputStream();
			Certificate cer = certificatefactory.generateCertificate(is);
			key = cer.getPublicKey();
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

}
