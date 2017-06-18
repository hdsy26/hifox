package org.edf.hifox.security.constant;

/**
 * 
 * @author WangYang
 *
 */
public class SecurityConstant {
	// 之前为加密数据
	public static final String METAINFO_PREFIX = " ";
	public static final String RANDOMKEY_PREFIX = METAINFO_PREFIX + " ";
	public static final String OBJECT_PREFIX = "	";
	public static final String OBJECT_UNIQUE_MARK_CHARSET_NAME = "UTF-8";
	
	public static final String SECURITY_MESSAGE_MODE_SIGN = "SIGN";
	public static final String SECURITY_MESSAGE_MODE_ENCRYPT = "ENCRYPT";
	
	public static final String SECURITY_MESSAGE_SIGNER_SUFFIX = "-signer";
	public static final String SECURITY_MESSAGE_VERIFIER_SUFFIX = "-verifier";
	
	public static final String SECURITY_MESSAGE_ENCIPHER_SUFFIX = "-encipher";
	public static final String SECURITY_MESSAGE_DECIPHER_SUFFIX = "-decipher";
	
}
