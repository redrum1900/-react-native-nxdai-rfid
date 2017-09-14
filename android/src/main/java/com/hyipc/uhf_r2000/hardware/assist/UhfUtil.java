package com.hyipc.uhf_r2000.hardware.assist;

import com.hyipc.util.Logger;

public class UhfUtil {
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}

		if (hexString.length() % 2 != 0) {
			if (hexString.length() == 1) {
				hexString = "0" + hexString;
			} else {
				int len = hexString.length();
				hexString = hexString.substring(0, len - 1) + "0" + hexString.substring(len - 1, len);
			}
		}

		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}
