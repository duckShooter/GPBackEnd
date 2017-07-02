package com.services;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {
	private static SessionFactory factory = setUpSessionFactory(); //Factory is needed along the application
	
	@SuppressWarnings("deprecation")
	private static SessionFactory setUpSessionFactory() throws HibernateException {
		return new Configuration().configure().buildSessionFactory();
	}
	
	public static SessionFactory getSessionFactory() {
		return factory;
	}
}