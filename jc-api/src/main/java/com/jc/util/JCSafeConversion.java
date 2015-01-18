package com.jc.util;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;

public final class JCSafeConversion {

	private static final Logger log = Logger.getLogger(JCSafeConversion.class);

	public static Integer bigDecimalToInteger(BigDecimal a) {
		return (a != null ? a.intValue() : null);
	}

	public static BigDecimal integerToBigDecimal(Integer number) {
		return number != null ? BigDecimal.valueOf(number.intValue()) : null;
	}

	public static int stringToInt(String number) {
		try {
			return (number != null ? Integer.parseInt(number) : 0);
		} catch (NumberFormatException e) {
			log.error("StringToInt: Unable to parse int from string: " + number);
			return 0;
		}
	}

	public static Integer stringToInteger(String number) {
		try {
			return (number != null ? Integer.parseInt(number) : null);
		} catch (NumberFormatException e) {
			log.error("Unable to parse int from string: " + number);
			e.printStackTrace();
			return null;
		}
	}

	public static String integerToString(Integer number) {
		if (number == null) return null;
		return Integer.toString(number);
	}

	public static Boolean stringToBoolean(String from) {
		if (from == null) return null;

		if (from.equalsIgnoreCase("true")) return Boolean.TRUE;
		else if (from.equalsIgnoreCase("false")) return Boolean.FALSE;

		if (from.equals("0")) return Boolean.FALSE;
		else if (from.equals("1")) return Boolean.TRUE;

		return null;
	}

	public static Boolean integerToBoolean(Integer from) {
		if (from == null) return null;
		else if (from == 0) return Boolean.FALSE;
		else if (from == 1) return Boolean.TRUE;
		return null;
	}

	public static boolean intToBoolean(int from) {
		return from >= 1;
	}

	/**
	 * @param value null/true/false
	 * @return null/1/2
	 */
	public static Integer booleanToInteger(Boolean value) {
		if (value == null) return null;//null
		else if (value != null && !value) return 1;//false
		else return 2;//true
	}

	/**
	 * @param value null/true/false
	 * @return 0/1/2
	 */
	public static int booleanToInt(Boolean value) {
		if (value == null) return 0;//null
		else if (value != null && !value) return 1;//false
		else return 2;//true
	}

	/**
	 * @param b true/false
	 * @return true = 1, false = 0
	 */
	public static int boolToInt(boolean b) {
		return b ? 1 : 0;
	}

	public static java.util.Date stringToUtilDate(String from) {
		if (from == null) return null;
		return new Date(Long.parseLong(from));
	}

	/**
	 * tagastab java.sql.Date objekti mis on konstrueeritud java.url.Date objekti long esitluse järgi, või nulli kui arumendiks saadud objekt oli null
	 *
	 * @param utilDate java.url.Date objekt millele vastavat java.sql.Date objekti me tahame saada
	 * @return kui argument oli null, siis tagastatakse ka null, muul juhul tehakse uus java.sql.Date objekt
	 */
	public static java.sql.Date utilToSqlDate(java.util.Date utilDate) {
		if (utilDate == null) return null;
		return new java.sql.Date(utilDate.getTime());
	}
}