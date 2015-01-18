package com.jc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class JCProperties {
	public static Properties getProperties(String address) throws IOException {
		Properties result = new Properties();
		InputStream istream = new FileInputStream(address);
		result.load(istream);
		istream.close();
		return result;
	}
}