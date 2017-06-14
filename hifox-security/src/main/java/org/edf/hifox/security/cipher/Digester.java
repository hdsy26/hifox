package org.edf.hifox.security.cipher;

public interface Digester extends Encipher {
	void update(byte[] input);
	void update(String input);
}
