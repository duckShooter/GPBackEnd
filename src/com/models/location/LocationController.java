package com.models.location;

import org.hibernate.Session;

import com.models.user.Profile;
import com.services.HibernateUtility;

public class LocationController {
	
	public static Location createLocation (double longitude , double latitude , String name) {
		Location location = new Location(longitude, latitude, name) ;
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		session.save(location);
		session.getTransaction().commit();
		session.close();
		return location ;
	}
	
	public static Location getLocation (int locationId) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		Location location = (Location)session.get(Location.class,locationId ) ;
		session.getTransaction().commit();
		session.close() ;
		return location ;
	}

}
