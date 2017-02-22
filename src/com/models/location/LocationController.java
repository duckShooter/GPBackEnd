package com.models.location;

import org.hibernate.Session;
import com.models.user.Profile;
import com.models.user.UserController;
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
	
	public static Area createArea (double redius , int ownerId , int locationId ) {
		Profile owner = UserController.getUser(ownerId) ;
		Location location = getLocation(locationId) ;
		Area area = new Area() ;
		area.setRedius(redius);
		area.setOwner(owner);
		area.setLocation(location);
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		session.save(area) ;
		session.getTransaction().commit();
		session.close() ;
		
		return area ;
	}
	
	
	public static Area getArae ( int areaId ) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		Area area = (Area)session.get(Area.class,areaId ) ;
		session.getTransaction().commit();
		session.close() ;
		return area ;
	}
	
	public static void addUserToArae ( int userId , int areaId ) {
		Area area = getArae(areaId) ;
		Profile user = UserController.getUser(userId) ;
		(area.getUsers()).add(user) ;
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		session.update(area);
		session.getTransaction().commit();
		session.close();
	}

}
