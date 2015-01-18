package com.jc.math.factorial.util;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Factory class manages the factorial algorithms in our system.
 *
 * @author wwoody
 */
public class FactorialAlgorithmFactory {

	private static HashMap<String, FactorialAlgorithm> mapping = new HashMap<>();
	private static HashMap<String, Class<? extends FactorialAlgorithm>> classMapping = new HashMap<>();
	private static FactorialAlgorithm defaultAlgorithm = new CachedFactorialImplementation();

	/**
	 * Static initializer registers some of my known classes
	 */
	static {
		try {
			Class.forName("com.chaosinmotion.factorial.LoopedFactorialImplementation");
			Class.forName("com.chaosinmotion.factorial.CachedFactorialImplementation");
		} catch (ClassNotFoundException e) {
			// Should never happen.
		}
	}

	/**
	 * Get the default algorithm for computing factorials
	 */
	public static FactorialAlgorithm getDefaultAlgorithm() {
		if (defaultAlgorithm == null) {
			// Warning: this will fail if for whatever reason
			// CachedFactorialImplementation
			// is not in the class path.
			defaultAlgorithm = getAlgorithm("cachedAlgorithm");
		}
		return defaultAlgorithm;
	}

	/**
	 * Get the factorial algorithm specified by name
	 */
	public static FactorialAlgorithm getAlgorithm(String name) {
		FactorialAlgorithm f = mapping.get(name);
		if (f == null) {
			// We haven't created an instance yet. Get it from the class
			// mapping.
			Class<? extends FactorialAlgorithm> c = classMapping.get(name);
			if (c != null) {
				// Create a new instance of the factorial algorithm specified
				try {
					f = c.newInstance();
					mapping.put(name, f);
					return f;
				} catch (InstantiationException | IllegalAccessException e) {
					// Log the error
					Logger.getLogger("com.chaosinmotion.factorial").log(
							Level.WARNING,
							"Unable to instantiate algorithm {0}, named {1}",
							new Object[]{c.getCanonicalName(), name});
				}
			}
			return getDefaultAlgorithm(); // return something.
		} else {
			return f;
		}
	}

	/**
	 * Register the class so we can construct a new instance if not already
	 * initialized
	 */
	public static void registerAlgorithm(String name,
	                                     Class<? extends FactorialAlgorithm> f) {
		classMapping.put(name, f);
	}
}
