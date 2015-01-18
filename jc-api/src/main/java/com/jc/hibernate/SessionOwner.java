package com.jc.hibernate;

import org.hibernate.Session;

public interface SessionOwner {
	public Session getSession();
	public void closeSession();
}