package org.edf.hifox.core.util;

import java.io.UnsupportedEncodingException;

import org.edf.hifox.core.constant.ErrorCodeConstant;
import org.edf.hifox.core.exception.FailureException;

/**
 * 
 * @author WangYang
 * 
 */
public final class StringUtil {

	private StringUtil() {

	}

	public static byte[] toBytes(String input, String charsetName) {
		try {
			if (input == null)
				return null;

			if (charsetName == null)
				return input.getBytes();

			return input.getBytes(charsetName);
		} catch (UnsupportedEncodingException e) {
			throw new FailureException(ErrorCodeConstant.E0001S060, new Object[] { charsetName }, e);
		}
	}

}
