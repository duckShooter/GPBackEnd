package com.models.event;

import java.sql.Timestamp;
import java.util.List;




import org.hibernate.Session;
import org.hibernate.query.Query;

import com.models.location.LocationController;
import com.models.user.Profile;
import com.models.user.UserController;
import com.services.HibernateUtility;

public class EventController {
	
	public static void createEvent (String name , String description , double radius 
			, int userId , String dateOfEvent ,  String deadline , boolean state , int locationId ) {
		
		Event event = new Event() ;
		event.setName(name);
		event.setDescription(description);
		event.setRadius(radius);
		event.setOwner(UserController.getUser(userId));
		event.setLocation(LocationController.getLocation(locationId));
		event.setDateOfEvent(dateOfEvent);
		event.setDeadline(deadline);
		event.setState(state);
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		session.save(event);
		session.getTransaction().commit();
		session.close();	
	}
	
	public static List <Event> getOwnersEvents (int ownerId) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		Query query = session.createQuery("from Event where owner_id = :ownerId ");
		query.setParameter("ownerId", ownerId) ;
		List list = query.list();
		session.close();
		return list ;
	}
	
	public static Event getEvent (int eventId) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		Event event = (Event)session.get(Event.class, eventId) ;
		session.getTransaction().commit();
		session.close();
		return event ;
		
	}

	public static void addUserToEvent ( int userId , int eventId) {
		Profile user = UserController.getUser(userId) ;
		Event event = getEvent(eventId) ;
		(event.getUsers()).add(user) ;
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		session.update(event);
		session.getTransaction().commit();
		session.close();
	}
	
	
	
	
}
