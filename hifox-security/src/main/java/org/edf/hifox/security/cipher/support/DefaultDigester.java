package org.edf.hifox.security.cipher.support;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.edf.hifox.core.exception.FailureException;
import org.edf.hifox.core.util.StringUtil;
import org.edf.hifox.core.util.SwapAreaUtil;
import org.edf.hifox.security.cipher.Digester;
import org.edf.hifox.security.constant.ErrorCodeConstant;
import org.edf.hifox.security.meta.CipherMetainfo;
import org.springframework.beans.factory.InitializingBean;

public class DefaultDigester implements Digester, InitializingBean {

	private String algorithm = "MD5";
	private String charsetName = "UTF-8";

	private CipherMetainfo metainfo;

	@Override
	public void update(byte[] input) {
		try {
			MessageDigest messageDigest = SwapAreaUtil.getMessageDigestObject();
			if (messageDigest == null) {
				messageDigest = MessageDigest.getInstance(algorithm);
				SwapAreaUtil.setMessageDigestObject(messageDigest);
			}
			messageDigest.update(input);
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
	public byte[] encrypt(byte[] input) {
		try {
			update(input);
			
			MessageDigest messageDigest = SwapAreaUtil.getMessageDigestObject();
			byte[] digest = messageDigest.digest();
			return digest;
		} catch (Exception e) {
			throw new FailureException(ErrorCodeConstant.E0001S071, new Object[]{e.getMessage()}, e);
		}
	}

	@Override
	public String encrypt(String input) {
		byte[] digest = encrypt(StringUtil.toBytes(input, charsetName));
		String digestHex = Hex.encodeHexString(digest);
		return digestHex;
	}

	@Override
	public CipherMetainfo metainfo() {
		return metainfo;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		metainfo = new CipherMetainfo();
		metainfo.setCharsetName(charsetName);
		metainfo.setAlgorithm(algorithm);

		String info = StringUtils.join(metainfo.getCharsetName(), "|",
				metainfo.getAlgorithm());

		metainfo.setInfo(info.getBytes(charsetName));
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}
	
}
