package com.jc.http;

import org.apache.log4j.Logger;

public enum HeaderContentType {

	XLS("application/vnd.ms-excel"),
	XLSX("application/vnd.ms-excel"),
	PDF("application/x-download"),
	ODS("application/vnd.oasis.opendocument.spreadsheet"),
	CSV("text/csv"),
	TXT("text/html"),

	MP3("audio/mpeg");

	public final String CONTENT_TYPE;
	private static final Logger log = Logger.getLogger(HeaderContentType.class);

	HeaderContentType(String contentType) {
		this.CONTENT_TYPE = contentType;
	}

	public String getContentType() {
		return CONTENT_TYPE;
	}

	public static HeaderContentType find(String contentType) throws IllegalArgumentException {
		for (HeaderContentType item : values()) {
			if (item.getContentType().equalsIgnoreCase(contentType)) return item;
		}
		String msg = "find: No such header content type: contentType=" + contentType;
		log.debug(msg);
		throw new IllegalArgumentException(msg);
	}

	public static HeaderContentType findByName(String extension) throws IllegalArgumentException {
		for (HeaderContentType item : values()) {
			if (item.name().equalsIgnoreCase(extension)) return item;
		}
		String msg = "find: No such header content type: extension=" + extension;
		log.debug(msg);
		throw new IllegalArgumentException(msg);
	}
}
