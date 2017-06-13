package org.edf.hifox.security.signer;

/**
 * RSA DES
 * @author WANGYANG
 *
 */
public interface Signer {
	void update(byte[] input);
	void update(String input);
	/**
	 * 私钥签名
	 * @return
	 */
	byte[] sign();
	/**
	 * 私钥签名
	 * @return
	 */
	String signString();
	/**
	 * 私钥签名
	 * @return
	 */
	String signString(String input);
}
