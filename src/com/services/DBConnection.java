package com.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class DBConnection {
	
	public static Session Connection () {
		SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory() ;
		Session session = sessionFactory.openSession() ;
		return session ;
	}

}
