package com.jc.math.statistics;

import com.jc.math.statistics.combinations.CombinationGenerator;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Vector;

/**
 * Example there are 25 numbers. How many combinations of 5 numbers exist with
 * the sum of 200
 *
 * @author some guy on the web
 */
@SuppressWarnings("rawtypes")
public final class ZenArchery {

	public static int getSum(Vector v) {
		int sum = 0;
		Integer n;
		for (int i = 0; i < v.size(); i++) {
			n = (Integer) v.elementAt(i);
			sum += n.intValue();
		}

		return sum;
	}

	@SuppressWarnings("unchecked")
	public static Vector compute(int[] array, int atATime, int desiredTotal) {
		int[] indices;
		CombinationGenerator gen = new CombinationGenerator(array.length,
				atATime);
		Vector results = new Vector();
		Vector combination;
		BigInteger numCombinations = gen.getTotal();
		System.out.println("Num combinations to test "
				+ numCombinations.toString());
		int sum;
		Integer intObj;
		while (gen.hasMore()) {
			combination = new Vector();
			indices = gen.getNext();
			for (int i = 0; i < indices.length; i++) {
				intObj = new Integer(array[indices[i]]);
				combination.addElement(intObj);
			}
			sum = getSum(combination);
			if (sum == desiredTotal) {
				Collections.sort(combination);
				if (!results.contains(combination)) {
					System.out.println(combination.toString());
					results.addElement(combination);
				}
			}
		}
		return results;
	}

	/*
	 * public static void org.genesysph.scbpis.main(String[] args) { int array[] = { 97, 101, 139,
	 * 41, 37, 31, 29, 89, 23, 19, 8, 13, 131, 19, 73, 97, 19, 139, 79, 67, 61,
	 * 17, 113, 127 }; Vector results = ZenArchery.compute(array, 5, 200);
	 * System.out.println("Num results " + results.size()); } //
	 */
}
