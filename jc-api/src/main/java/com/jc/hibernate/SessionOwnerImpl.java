package com.jc.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.io.Serializable;

public final class SessionOwnerImpl implements SessionOwner, Serializable {

	private static final Logger log = Logger.getLogger(SessionOwnerImpl.class);
	private static final long serialVersionUID = 8997389573342214318L;

    private final SessionCreator sessionCreator;

	public SessionOwnerImpl(SessionCreator sessionCreator) {
        this.sessionCreator = sessionCreator;
    }

	private transient Session _session;
	public Session getSession() {
		if (null == _session) {
			log.debug("getSession: new session");
			_session = sessionCreator.openSession();
		}
		return _session;
	}

	public void closeSession() {
		log.debug("closeSession");
		if (_session != null) {
			SessionCreator.close(_session);
			_session = null;
		}
	}
}