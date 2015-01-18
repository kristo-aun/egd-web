package com.jc.math.statistics.permutations;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

/**
 * systematically generates permutations from all list elements
 */
public final class PermsFromList {

	private final PermutationGenerator pg;
	private final List list;

	public PermsFromList(List list) {
		pg = new PermutationGenerator(list.size());
		this.list = list;
	}

	public boolean hasMore() {
		return pg.hasMore();
	}

	public BigInteger getNumLeft() {
		return pg.getNumLeft();
	}

	public BigInteger getTotal() {
		return pg.getTotal();
	}

	public void reset() {
		pg.reset();
	}

	public List getNext() {
		List result = new LinkedList();

		int[] a = pg.getNext();
		for (int anA : a) {
			result.add(list.get(anA));
		}

		return result;
	}
}