package com.jc.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class for various static operations with arrays
 */
public final class JCArray {

	/**
	 * Reallocates an array with a new size, and copies the contents of the old
	 * array to the new array.
	 *
	 * @param oldArray the old array, to be reallocated.
	 * @param newSize  the new array size.
	 * @return A new array with the same contents.
	 */
	@SuppressWarnings("SuspiciousSystemArraycopy")
	public static Object resizeArray(Object oldArray, int newSize) {
		int oldSize = java.lang.reflect.Array.getLength(oldArray);
		Class elementType = oldArray.getClass().getComponentType();
		Object newArray = java.lang.reflect.Array.newInstance(elementType,
				newSize);
		int preserveLength = Math.min(oldSize, newSize);
		if (preserveLength > 0) {
			System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
		}
		return newArray;
	}

	public static void print(int[] arr) {
		String print = "";
		for (int anArr : arr) {
			print += anArr + " ";
		}

		System.out.println("------");
		System.out.println(print);
		System.out.println("------");

	}

	/**
	 * @param arr - two-dimensional array
	 */
	public static void print(double[][] arr) {
		for (double[] anArr : arr) {
			for (int j = 0; j < arr[0].length; j++) {
				System.out.print(anArr[j] + "	");
			}
			System.out.println();
		}
	}

	/**
	 * @param arr - one-dimensional array
	 */
	public static void print(double[] arr) {
		for (double anArr : arr) {
			System.out.print(anArr + " ");
		}
	}

	public static List<Map> resultSetToArrayList(ResultSet rs)
			throws SQLException {

		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		List<Map> results = new ArrayList<>();

		while (rs.next()) {

			Map row = new HashMap();

			results.add(row);

			for (int i = 1; i <= columns; i++) {

				row.put(md.getColumnName(i), rs.getObject(i));
			}
		}
		return results;
	}
}