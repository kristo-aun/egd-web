package com.jc.util;

import org.apache.log4j.Logger;

public final class JCClass {

	private static final Logger log = Logger.getLogger(JCClass.class);

	public static Class getFieldClassSafe(Class entityClass, String fieldName) {
		try {
			return entityClass.getDeclaredField(fieldName).getType();
		} catch (NoSuchFieldException e) {
			log.error("getFieldClassSafe: NoSuchFieldException: entityClass=" + entityClass +
					", fieldName=" + fieldName +
					", msg=" + e.getMessage(), e
			);
			return null;
		}
	}
}