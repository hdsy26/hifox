package org.edf.hifox.security.signer;

public interface Verifier {
	void update(byte[] input);
	void update(String input);
	/**
	 * 公钥验签
	 * @return
	 */
	boolean verify(byte[] signvalue);
	/**
	 * 公钥验签
	 * @return
	 */
	boolean verify(String signvalueString);
	
	/**
	 * 公钥验签
	 * @return
	 */
	boolean verify(String input, String signvalueString);
}
