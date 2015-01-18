package com.jc.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * useful static operations regarding date & time
 */
public final class JCDateTime {

	public static final String EN_US_SEC_T = "yyyy-MM-dd'T'HH-mm-ss";
	public static final DateFormat dfEnUsSecT = new SimpleDateFormat(EN_US_SEC_T);

	public static final String EN_US_THOUSANDTH_ = "yyyy-MM-dd_HH-mm-ss-SSS";
	public static final DateFormat dfEnUsThousandth_ = new SimpleDateFormat(EN_US_THOUSANDTH_);

	public static final String EN_US = "yyyy-MM-dd HH:mm";
	public static final DateFormat dfEnUs = new SimpleDateFormat(EN_US);

	public static final String EN_US_SEC = "yyyy-MM-dd HH:mm:ss";
	public static final DateFormat dfEnUsSec = new SimpleDateFormat(EN_US_SEC);

	public static final String EE_ET = "dd.MM.yyyy";
	public static final DateFormat dfEeEt = new SimpleDateFormat(EE_ET);

	public static final String EN_US_THOUSANDTH = "yyyy-MM-dd HH:mm:ss:SSS";
	public static final DateFormat dfEnUsThousandth = new SimpleDateFormat(EN_US_THOUSANDTH);

	/**
	 * Return date and time string formatted as yyyy-MM-dd HH:mm
	 */
	public static String now() {
		return dfEnUs.format(new java.util.Date());
	}

	public static String now(String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cal.getTime());
	}

	/**
	 * Return date and time string formatted as yyyy-MM-dd HH:mm:ss
	 */
	public static String nowIncludingSec() {
		return dfEnUsSec.format(new java.util.Date());
	}

	/**
	 * Return date and time string formatted as yyyy-MM-dd HH:mm:ss:SSS
	 */
	public static String nowIncludingThousandth() {
		return dfEnUsThousandth.format(new java.util.Date());
	}

	/**
	 * Return date and time string formatted as yyyy-MM-dd'T'HH-mm-ss
	 */
	public static String nowstamp() {
		return dfEnUsSecT.format(new java.util.Date());
	}

	/**
	 * Return current unix time
	 */
	public long nowlong() {
		return new java.util.Date().getTime();
	}

	public static String format(java.util.Date date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
}
