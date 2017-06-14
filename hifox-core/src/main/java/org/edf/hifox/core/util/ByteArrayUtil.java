package org.edf.hifox.core.util;

import java.io.UnsupportedEncodingException;

import org.apache.http.util.ByteArrayBuffer;
import org.edf.hifox.core.constant.ErrorCodeConstant;
import org.edf.hifox.core.exception.FailureException;

/**
 * 
 * @author WangYang
 *
 */
public final class ByteArrayUtil {
	
	private ByteArrayUtil() {
		
	}

	public static byte[] join(byte[]... byteArrays) {
		if (byteArrays == null)
			throw new NullPointerException("byte array is null");
		
		ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(0);
		
		for (byte[] byteArray : byteArrays) {
			byteArrayBuffer.append(byteArray, 0, byteArray.length);
		}
		
		return byteArrayBuffer.toByteArray();
	}
	
	public static String toString(byte[] input, String charsetName) {
		try {
			if (input == null)
				return null;

			if (charsetName == null)
				return new String(input);

			return new String(input, charsetName);
		} catch (UnsupportedEncodingException e) {
			throw new FailureException(ErrorCodeConstant.E0001S060, new Object[] { charsetName }, e);
		}
	}

}
