package com.jc.math.statistics.combinations;

import java.util.ArrayList;
import java.util.List;

/**
 * systematically generates combinations from list
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class KCombsFromList extends KCombs {

	private final List list;

	public KCombsFromList(List list, int k) {
		super(list.size(), k);
		this.list = list;
	}

	@Override
	public List getNext() {
		List result = new ArrayList();
		int[] index = super.cg.getNext();

		for (int i = 0; i < index.length; i++) {
			result.add(list.get(index[i]));
		}

		return result;
	}
}
