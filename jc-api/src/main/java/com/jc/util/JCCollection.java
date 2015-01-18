package com.jc.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * class for various static operations with lists
 */
public final class JCCollection {

	public static void print(List out) {
		System.out.println("----------");
		if (out != null && out.size() > 0) {
			for (Object anOut : out) {
				System.out.println(anOut);
			}
		}
		System.out.println("----------");
	}

	/**
	 * joins list with string delimiter
	 */
	public static String join(List list, String delimiter) {
		String result = "";

		for (int i = 0; i < list.size(); i++) {
			if (i != 0) {
				result += delimiter;
			}
			result += list.get(i).toString();
		}

		return result;
	}

	/**
	 * merges two lists and loses recurring elements
	 */
	public static List<String> merge(List<String> a, List<String> b) {
		List<String> result = new ArrayList<>(a);
		result.removeAll(b);
		result.addAll(b);
		return result;
	}

	public static String toString(Collection col) {
		if (col == null) return null;
		StringBuilder result = new StringBuilder("[");
		for (Object p : col) {
			if (result.length() > 1)
				result.append(", ");
			result.append(p);
		}
		result.append("]");
		return result.toString();
	}

	@SafeVarargs
	public static <T> Collection<T> collectionSum(Collection<T> ... carr) {
		if (carr == null || carr.length < 1) return null;
		Collection<T> result = null;
		for (Collection<T> p : carr) {
			if (result == null && p != null) {
				result = p;
			} else if (p != null) {
				result.addAll(p);
			}
		}
		return result;
	}

	public static List<Integer> toIntList(String commaSeparated) {
		String[] arr = commaSeparated.split(",");
		List<Integer> result = new ArrayList<>();
		for (String p : arr) {
			result.add(Integer.parseInt(p));
		}
		return result;
	}
}