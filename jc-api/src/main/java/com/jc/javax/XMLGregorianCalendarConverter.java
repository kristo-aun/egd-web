package com.jc.javax;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Utility class for converting between XMLGregorianCalendar and java.hibernate.Date
 */
public final class XMLGregorianCalendarConverter {

	/**
	 * Needed to create XMLGregorianCalendar instances
	 */
	private static DatatypeFactory df = null;

	static {
		try {
			df = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException dce) {
			throw new IllegalStateException(
					"Exception while obtaining DatatypeFactory instance", dce);
		}
	}

	/**
	 * Converts an XMLGregorianCalendar to an instance of java.hibernate.Date
	 *
	 * @param xgc Instance of XMLGregorianCalendar or a null reference
	 * @return java.hibernate.Date instance whose value is based upon the value in
	 *         the xgc parameter. If the xgc parameter is null then this method
	 *         will simply return null.
	 */
	public static Date asDate(XMLGregorianCalendar xgc) {
		if (xgc == null) {
			return null;
		} else {
			return xgc.toGregorianCalendar().getTime();
		}
	}

	/**
	 * Converts a java.hibernate.Date into an instance of XMLGregorianCalendar
	 *
	 * @param date Instance of java.hibernate.Date or a null reference
	 * @return XMLGregorianCalendar instance whose value is based upon the value
	 *         in the date parameter. If the date parameter is null then this
	 *         method will simply return null.
	 */
	public static XMLGregorianCalendar asXMLGregorianCalendar(
			Date date) {
		if (date == null) {
			return null;
		} else {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTimeInMillis(date.getTime());
			return df.newXMLGregorianCalendar(gc);
		}
	}
}