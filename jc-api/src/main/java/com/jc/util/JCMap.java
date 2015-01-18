package com.jc.util;

import com.jc.structure.pojo.IntIDStringTitle;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class JCMap {

	/**
	 * Factory
	 */
	public static Map.Entry<Integer, String> asEntry(int key, String value) {
		return new AbstractMap.SimpleEntry<>(key, value);
	}

	/**
	 * Factory - näiteks faili nimi & baidid
	 */
	public static Map.Entry<String, byte[]> asEntry(String key, byte[] value) throws IllegalArgumentException {
		if (key == null) throw new IllegalArgumentException("asEntry: key == null");
		return new AbstractMap.SimpleEntry<>(key, value);
	}

	public static Map.Entry<String, String> asEntry(String key, String value) {
		return new AbstractMap.SimpleEntry<>(key, value);
	}

	public static int keyOfFirstOccurence(Map map, Object value) {
		int result = -1;

		if (map != null) {
			for (int i = 0; i < map.size(); i++) {
				if (map.get(i) == value) {
					result = i;
				}
			}
		}
		return result;
	}

	public static void print(Map out) {
		System.out.println("----------");
		if (out != null && out.size() > 0) {
			for (int i = 0; i < out.size(); i++) {
				System.out.println(out.get(i));
			}
		}
		System.out.println("----------");
	}

	public static Map<Integer, String> asMap(int[] arr) {
		Map<Integer, String> result = new LinkedHashMap<>(arr.length);
		for (int p : arr) {
			result.put(p, String.valueOf(p));
		}
		return result;
	}

	public static Map<Integer, String> toIntLinkedHashMap(int from, int to) {
		if (from > to) throw new IllegalArgumentException("toIntLinkedHashMap: from > to");
		Map<Integer, String> result = new LinkedHashMap<>();
		for (int i = from; i <= to; i++) {
			result.put(i, String.valueOf(i));
		}
		return result;
	}

	public static Map<Integer, String> toMap(IntIDStringTitle[] arr) {
		Map<Integer, String> result = new LinkedHashMap<>();
		for (IntIDStringTitle p : arr) {
			result.put(p.getId(), p.getTitle());
		}
		return result;
	}
}