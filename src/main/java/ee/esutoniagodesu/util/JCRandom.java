package ee.esutoniagodesu.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * class for creating random variables of any kind
 */
public final class JCRandom {

	/**
	 * generates random integet value
	 *
	 * @param start start
	 * @param end   end
	 * @return value between start and end
	 */
	public static int between(int start, int end) {
		int result;
		end += 1;

		if (end > start) {
			Random rand = new Random();
			int interval = Math.abs(end - start);
			result = start + rand.nextInt(interval);
		} else if (end == start) {
			result = start;
		} else {
			throw new IllegalArgumentException();
		}

		return result;
	}

	/**
	 * @param seed seed
	 * @return value between 0 and seed
	 */
	public static int integer(int seed) {
		Random rand = new Random();
		return rand.nextInt(seed);
	}

	/**
	 * generates double matrix with randomly initiated relations.
	 *
	 * @param ydim  - y dimension
	 * @param xdim  - x dimension
	 * @param count - how many relations
	 * @param value - relation value
	 * @return double matrix
	 */
	public static double[][] getMatrix(int ydim, int xdim, int count,
	                                   double value) {
		double[][] result = new double[ydim][xdim];

		List<Integer> keys = new ArrayList<>();
		int n = ydim * xdim;
		for (int i = 0; i < n; i++) {
			keys.add(i);
		}

		Collections.shuffle(keys);

		int pos, y, x;

		for (int i = 0; i < count; i++) {
			pos = keys.get(i);
			x = pos % xdim;
			y = (pos - x) / xdim;
			result[y][x] = value;
		}

		return result;
	}
}
