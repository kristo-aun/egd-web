package com.jc.spring;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.WebRequest;

import java.util.Iterator;

public final class JCWebRequest {

	private static final Logger log = Logger.getLogger(JCWebRequest.class);

	public static void explainHeader(WebRequest wr) {
		log.debug("explainHeader");
		Iterator<String> p = wr.getHeaderNames();
		while (p.hasNext()) {
			String pitem = p.next();
			System.out.println(pitem + "=" + wr.getHeader(pitem));
		}
	}

	public static void explainParameters(WebRequest wr) {
		log.debug("explainParameters");
		Iterator<String> p = wr.getParameterNames();
		while (p.hasNext()) {
			String pitem = p.next();
			System.out.println(pitem + "=" + wr.getParameter(pitem));
		}
	}

	public static void explainAll(WebRequest wr) {
		log.debug("explainAll");
		System.out.println("explainAll: contextPath=" + wr.getContextPath() +
				", remoteUser=" + wr.getRemoteUser() +
				", sessionId=" + wr.getSessionId());

		explainHeader(wr);
		explainParameters(wr);
	}

}
