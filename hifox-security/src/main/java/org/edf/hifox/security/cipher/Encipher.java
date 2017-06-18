package org.edf.hifox.security.cipher;

import org.edf.hifox.security.meta.CipherMetainfo;


/**
 * 加密接口
 * @author WANGYANG
 *
 */
public interface Encipher {
	byte[] encrypt(byte[] input);
	String encrypt(String input);
	CipherMetainfo metainfo();
	
}
