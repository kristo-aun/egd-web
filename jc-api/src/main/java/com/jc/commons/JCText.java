package com.jc.commons;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;

public final class JCText {
	/**
	 * Replaces \n characters with space
	 */
	public static String jcremoveNewline(String string) {
		if (string == null) return null;
		return string.replaceAll("\n", " ");
	}

	public static String toHex(String string) throws UnsupportedEncodingException {
		return toHex(string, "UTF-8");
	}

	public static String toHex(String string, String charset) throws UnsupportedEncodingException {
		return Hex.encodeHexString(string.getBytes(charset));
	}

	public static String hexToString(String hex) throws DecoderException, UnsupportedEncodingException {
		return hexToString(hex, "UTF-8");
	}

	public static String hexToString(String hex, String charset) throws DecoderException, UnsupportedEncodingException {
		byte[] bytes = Hex.decodeHex(hex.toCharArray());
		return new String(bytes, charset);
	}

	/**
	 * A character is described with code points 1-256, like in ASCII or ANSI
	 */
	public static String hexToString256(String hex) {
		StringBuilder sb = new StringBuilder();
		char[] hexData = hex.toCharArray();

		for (int count = 0; count < hexData.length - 1; count += 2) {
			int firstDigit = Character.digit(hexData[count], 16);
			int lastDigit = Character.digit(hexData[count + 1], 16);
			int decimal = firstDigit * 16 + lastDigit;
			sb.append((char) decimal);
		}
		return sb.toString();
	}
}