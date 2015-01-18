package com.jc.math.statistics.combinations;

import com.jc.math.factorial.JCFactorial;

import java.math.BigInteger;

/**
 * systematically generates combinations from matrix
 * <p/>
 * in order to generate all possible variations of 6x8 matrix (0->48 relations)
 * it would take 25 years for an average laptop (2.26GHz DC)
 * <p/>
 * how many variations there are of each sum of relations? 0: 1 1: 48 2: 1128 3:
 * 17296 4: 194580 5: 1712304 6: 12271512 7: 73629072 8: 377348994 9: 1677106640
 * 10: 6540715896 11: 22595200368 12: 69668534468 13: 192928249296 14:
 * 482320623240 15: 1093260079344 16: 2254848913647 17: 4244421484512 18:
 * 7309837001104 19: 11541847896480 20: 16735679449896 21: 22314239266528 22:
 * 27385657281648 23: 30957699535776 24: 32247603683100 25: 30957699535776 26:
 * 27385657281648 27: 22314239266528 28: 16735679449896 29: 11541847896480 30:
 * 7309837001104 31: 4244421484512 32: 2254848913647 33: 1093260079344 34:
 * 482320623240 35: 192928249296 36: 69668534468 37: 22595200368 38: 6540715896
 * 39: 1677106640 40: 377348994 41: 73629072 42: 12271512 43: 1712304 44: 194580
 * 45: 17296 46: 1128 47: 48 48: 1
 */
public final class KCombsFromMatrix {

	private final int ydim;
	private final int xdim;
	private final int count;
	private final CombinationGenerator kc;

	public KCombsFromMatrix(int ydim, int xdim, int count) {
		this.ydim = ydim;
		this.xdim = xdim;

		if (ydim <= 0 || xdim <= 0) {
			throw new IllegalArgumentException();
		}

		if (count >= 0 && count <= ydim * xdim) {
			this.count = count;
			kc = new CombinationGenerator(ydim * xdim, count);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public int getYdim() {
		return ydim;
	}

	public int getXdim() {
		return xdim;
	}

	public int getCount() {
		return count;
	}

	/**
	 * 6x8 matrix, from 0 to 48, there is 281 474 976 710 656 different
	 * combinations
	 */
	public BigInteger getTotal() {
		BigInteger result = BigInteger.ZERO;
		int n = ydim * xdim;
		result = result.add(JCFactorial.kcombinations(n, count));
		return result;
	}

	public double[][] getNext(double value) {

		double[][] result = new double[ydim][xdim];

		int[] keys = kc.getNext();

		int x, y;
		for (int k = 0; k < keys.length; k++) {
			x = keys[k] % xdim;
			y = (keys[k] - x) / xdim;
			result[y][x] = value;
		}

		return result;
	}
}
