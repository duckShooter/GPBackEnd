package com.models.event;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.models.location.LocationController;
import com.models.user.Profile;
import com.models.user.UserController;
import com.services.HibernateUtility;

public class EventController {	
	public static Event createEvent (String name , String description , double radius 
			, int userId , String dateOfEvent ,  String deadline  , String imageURL , boolean state , int locationId ) {
		
		Event event = new Event() ;
		event.setName(name);
		event.setDescription(description);
		event.setRadius(radius);
		event.setOwner(UserController.getUser(userId));
		event.setLocation(LocationController.getLocation(locationId));
		event.setDateOfEvent(dateOfEvent);
		event.setDeadline(deadline);
		event.setState(state);
		event.setImageURL(imageURL);
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		session.save(event);
		session.getTransaction().commit();
		session.close();
		return event ;
	}
	
	/*public static List <Event> getOwnersEvents (int ownerId) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		Query query = session.createQuery("from Event where owner_id = :ownerId ");
		query.setParameter("ownerId", ownerId) ;
		List list = query.list();
		session.close();
		return list ;
	}*/
	
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
	
	public static List <Profile> getEventUsers ( int eventId ) {
		Event event = getEvent(eventId) ;
		return event.getUsers() ;
	}
	
	
	public static Profile getEventOwner ( int eventId ) {
		Event event = getEvent(eventId) ;
		return event.getOwner() ;
	}
	
	
	
	
}
