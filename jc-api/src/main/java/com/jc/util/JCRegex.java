package com.jc.util;

public final class JCRegex {

	public static String prettify(String uglyId) {

		//convert all _ to spaces
		String a = uglyId.replace("_", " ").toLowerCase();

		//get rid of tabs
		a = a.replace(" ", " ");

		//get rid of double spaces
		while (a.contains("  ")) {
			a = a.replace("  ", " ");
		}

		//replace spaces
		a = a.replace(" ", "_").toLowerCase();

		//remove all but letters, numbers and _
		String b = a.replaceAll("[^a-zA-Z0-9_]+", "");

		//get rid of double _
		while (b.contains("__")) {
			b = b.replace("__", "_");
		}

		return b;
	}
}