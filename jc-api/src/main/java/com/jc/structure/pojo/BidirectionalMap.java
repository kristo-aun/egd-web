package com.jc.structure.pojo;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @param <KeyType>
 * @param <ValueType>
 */
public final class BidirectionalMap<KeyType, ValueType> implements Serializable {

	private static final long serialVersionUID = -6408287724695353814L;
	private final Map<KeyType, ValueType> keyToValueMap = new LinkedHashMap<>();
	private final Map<ValueType, KeyType> valueToKeyMap = new LinkedHashMap<>();

	public Collection<ValueType> values() {
		return valueToKeyMap.keySet();
	}

	public Collection<KeyType> keys() {
		return keyToValueMap.keySet();
	}

	public void put(KeyType key, ValueType value) throws IllegalStateException {
		if (keyToValueMap.containsKey(key) || valueToKeyMap.containsKey(value))
			throw new IllegalStateException("topelt entrid ei ole lubatud");
		keyToValueMap.put(key, value);
		valueToKeyMap.put(value, key);
	}

	public ValueType removeByKey(KeyType key) {
		ValueType removedValue = keyToValueMap.remove(key);
		valueToKeyMap.remove(removedValue);
		return removedValue;
	}

	public KeyType removeByValue(ValueType value) {
		KeyType removedKey = valueToKeyMap.remove(value);
		keyToValueMap.remove(removedKey);
		return removedKey;
	}

	public boolean containsKey(KeyType key) {
		return keyToValueMap.containsKey(key);
	}

	public boolean containsValue(ValueType value) {
		return keyToValueMap.containsValue(value);
	}

	public KeyType getKey(ValueType value) {
		return valueToKeyMap.get(value);
	}

	public ValueType get(KeyType key) {
		return keyToValueMap.get(key);
	}

	public int size() {
		if (keyToValueMap.size() == valueToKeyMap.size())
			return keyToValueMap.size();
			//ei tohi kunagi juhtuda. võimaik, et võeti keys() või values() ja tehti add/remove
		else throw new IllegalStateException("size: " + keyToValueMap.size() + "/" + valueToKeyMap.size());
	}

	public void clear() {
		keyToValueMap.clear();
		valueToKeyMap.clear();
	}

	public String toString() {
		return "BidirectionalMap{" +
				"keyToValueMap=" + keyToValueMap +
				", valueToKeyMap=" + valueToKeyMap +
				'}';
	}
}