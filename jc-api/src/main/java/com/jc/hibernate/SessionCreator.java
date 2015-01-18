package com.jc.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;

public final class SessionCreator {

	private static final Logger log = Logger.getLogger(SessionCreator.class);

	private final SessionFactory sessionFactory;
	private ServiceRegistry serviceRegistry;

	public SessionCreator() {
        this(new Configuration());
    }

    public SessionCreator(Configuration configuration) {
        try {
            configuration.configure();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(getServiceRegistry());
        } catch (Throwable ex) {
            log.error("SessionFactoryImpl.static: exc=" + ex.getMessage(), ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

	private ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void closeFactory() throws HibernateException {
		if (sessionFactory != null) {
			try {
				sessionFactory.close();
			} catch (HibernateException e) {
				log.error("closeFactory: Couldn't close SessionFactory", e);
				e.printStackTrace();
				throw e;
			}
		}
	}

	public static void close(Session session) {
		log.debug("close");
		if (session != null) {
			try {
				Connection con = session.close();
				if (con != null) con.close();
			} catch (Exception e) {
				log.error("close: Couldn't close Session", e);
			}
		}
	}

	public Session openSession() {
		return getSessionFactory().openSession();
	}
}