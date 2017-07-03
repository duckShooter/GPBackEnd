package com.models.event;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.models.location.Location;
import com.models.location.LocationController;
import com.models.notifications.EventInvitationNotification;
import com.models.notifications.NotificationController;
import com.models.user.Profile;
import com.models.user.UserController;
import com.services.HibernateUtility;

public class EventController {	
	public static Event createEvent (String name , String description , double radius 
			, int userId , String dateOfEvent ,  String deadline , int locationId , String image ) {
		
		Event event = new Event() ;
		event.setName(name);
		event.setDescription(description);
		//event.setRadius(radius);
		event.setOwner(UserController.getUser(userId));
		//event.setLocation(LocationController.getLocation(locationId));
		event.setArea(LocationController.createArea(radius, userId, locationId, image));
		event.setDateOfEvent(dateOfEvent);
		event.setDeadline(deadline);
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
	
	public static Suggestion getSuggestion (int suggestionId) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		Suggestion suggestion = (Suggestion)session.get(Suggestion.class, suggestionId) ;
		session.getTransaction().commit();
		session.close();
		return suggestion ;
		
	}
	
	@SuppressWarnings("deprecation")
	public static List <Event> markEventState (List <Event> events ) {
		for ( int i = 0 ; i < events.size() ; i++ ) {
			Timestamp time = Timestamp.valueOf(events.get(i).getDeadline().replace("T", " ")) ;
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			if (timestamp.getHours()+6 > 24 )
				timestamp.setHours((timestamp.getHours()+6)-24);
			else 
				timestamp.setHours(timestamp.getHours()+6);
			if (time.after(timestamp)) events.get(i).eventState = 1 ;
			else events.get(i).eventState = 2 ;
		}
		
		return events ;
	}
	
	@SuppressWarnings("deprecation")
	public static List <Event> getUnfinishedEvents (List <Event> events ) {
		
		List <Event> unfinished = new ArrayList<Event>();
		for ( int i = 0 ; i < events.size() ; i++ ) {
			Timestamp time = Timestamp.valueOf(events.get(i).getDeadline().replace("T", " ")) ;
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			if (timestamp.getHours()+6 > 24 )
				timestamp.setHours((timestamp.getHours()+6)-24);
			else 
				timestamp.setHours(timestamp.getHours()+6);
			if (time.after(timestamp)) { 
				events.get(i).eventState = 1 ;
				unfinished.add(events.get(i)) ;
			}
		}
		return unfinished ;
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
	
	public static void sendInvitation ( int friendId , int eventId ) {
		Profile user = UserController.getUser(friendId) ;
		Event event = getEvent(eventId) ;
		if (UserController.searchArray(event.getInvitations(), user) != -1 || UserController.searchArray(event.getUsers(), user) != -1 )
			return ;
		(event.getInvitations()).add(user) ;
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		session.update(event);
		session.persist(new EventInvitationNotification(user, event)); //Notification
		session.getTransaction().commit();
		session.close() ;
	}
	
	public static List <Event> setUserStatus (List <Event> events , int status ){
		for ( int i = 0 ; i < events.size() ; i++ ) {
			events.get(i).userStatus = status ;
		}
		return events ;
	}
	
	public static List <Profile> getEventUsers ( int eventId ) {
		Event event = getEvent(eventId) ;
		List <Profile> allUsers = new ArrayList <Profile> () ;
		allUsers.add(event.getOwner()) ;
		allUsers.addAll(event.getUsers()) ;
		return allUsers ;
	}
	
	
	public static List <Profile> getEventUsersWithLocation (int eventId ) {
		List <Profile> list = getEventUsers(eventId) ;
		for ( int i = 0 ; i < list.size() ; i++ ) {
			Location location = UserController.getUserLastLocation (list.get(i).getUser_Id());
			list.get(i).latitude = location.getLatitude() ;
			list.get(i).longitude = location.getLongitude() ;
		}
		return list ;
	}
	
	
	public static Profile getEventOwner ( int eventId ) {
		Event event = getEvent(eventId) ;
		return event.getOwner() ;
	}
	
	public static int searchForEvent (List <Event> events , int eventId ) {
		
		for ( int i = 0 ; i < events.size() ; i++  ) {
			if (events.get(i).getEvent_id() == eventId )
				return i ;
		}
		
		return -1 ;
	}
	
	public static List <Profile> listOfUsersToInvit ( int userId , int eventId ) {
		
		Profile user = UserController.getUser(userId);
		List <Profile> friends = user.getFriends() ;
		for ( int i = 0 ; i < friends.size() ; i++ ) {
			if (searchForEvent(friends.get(i).getEventInvitations(), eventId) != -1 ) friends.get(i).eventStatus = 1 ;
			else if (searchForEvent(friends.get(i).getEvents(), eventId) != -1 )  friends.get(i).eventStatus = 2 ;
			else friends.get(i).eventStatus = 3 ;
		}
		
		return friends ;
	}
	
	public static void removeFromInvitationList ( int eventId , int userId ) {
		Event event = getEvent(eventId) ;
		Profile user = UserController.getUser(userId) ;
		int index = UserController.searchArray(event.getInvitations(),user);
		event.getInvitations().remove(index) ;
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		session.update(event);
		session.getTransaction().commit();
		session.close() ;
	}

	public static void removeEventContain (Event event) {
		event.getUsers().clear();
		event.getInvitations().clear();
		event.setOwner(null);
		List <Suggestion> suggestions = event.getSuggestions() ;
		for ( int i=0 ; i < suggestions.size() ; i++ ) {
			deleteSuggestion(suggestions.get(i).getSuggestion_id());
		}
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		session.update(event);
		session.getTransaction().commit();
		session.close() ;
	}
	
	
	public static void deleteEvent ( int eventId ) {
		Event event = getEvent(eventId) ;
		removeEventContain (event) ;
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		session.delete(event);
		NotificationController.notifyEventDeletion(event); //Notification
		session.getTransaction().commit();
		session.close() ;
		LocationController.deleteArea(event.getArea().getArea_id());
	}
	
	public static String addSuggestion ( int userId , int eventId , String time ) {
		Event event = getEvent(eventId) ;
		Profile usre = UserController.getUser(userId) ;
		if (event.getOwner().getUser_Id() == userId )
			return "Failed" ;
		
		Suggestion suggestion = new Suggestion() ;
		suggestion.setProfile(usre);
		suggestion.setEvent(event);
		suggestion.setTime(time);
		
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		session.save(suggestion);
		session.getTransaction().commit();
		session.close() ;
		
		return "Done" ;
	}
	
	public static int searchSuggestion ( List <Suggestion> suggestions , int id ) {
		for ( int i = 0 ; i < suggestions.size() ; i++ ) {
			if ( suggestions.get(i).getSuggestion_id() == id ) 
				return i ;
		}
		
		return -1 ;
	}
	
	public static String removeSuggestion (int userId , int eventId , int suggestionId ){
		Event event = getEvent(eventId) ;
		Suggestion suggestion = getSuggestion(suggestionId) ;
		if (event.getOwner().getUser_Id() != userId )
			return "Failed" ;
		int index = searchSuggestion (event.getSuggestions(),suggestionId) ;
		if ( index == -1 ) 
			return "Failed" ;
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		session.clear() ;
		session.delete(suggestion);;
		session.getTransaction().commit();
		session.close() ;
		
		return "Done" ;
	}
	
	public static String updateEventTime (int userId , int eventId , int suggestionId) {
		Event event = getEvent(eventId) ;
		Suggestion suggestion = getSuggestion(suggestionId) ;
		if (event.getOwner().getUser_Id() != userId )
			return "Failed" ;
		int index = searchSuggestion (event.getSuggestions(),suggestionId) ;
		if ( index == -1 ) 
			return "Failed" ;
		
		event.setDeadline(suggestion.getTime());
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		session.update(event);
		NotificationController.notifyEventEditing(event); //Notification
		session.getTransaction().commit();
		session.close() ;
		
		return "Done" ;
		
	}
	
	public static String acceptSuggestion (int userId , int eventId , int suggestionId ) {
		if (updateEventTime(userId , eventId, suggestionId).equals("Failed") ) 
			return "Failed" ;
		if (removeSuggestion(userId, eventId, suggestionId).equals("Done")) {
			return "Done" ;
		} else 
			return "Failed" ;
	}
	
	public static String  rejectSuggestion (int userId , int eventId , int suggestionId ) {
		if (removeSuggestion(userId, eventId, suggestionId).equals("Done")) {
			return "Done" ;
		}
		else return "Failed" ;
	}
	
	
	public static List <Suggestion> getSuggestions ( int eventId ) {
		Event event = getEvent(eventId) ;
		return event.getSuggestions() ;
	}
	
	public static void deleteSuggestion ( int suggestionId ) {
		Suggestion suggestion = getSuggestion(suggestionId) ;
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		session.delete(suggestion);;
		session.getTransaction().commit();
		session.close() ;
		
	}
	
	public static int userStatusForEvent(Profile user, int eventId) { //Personal
		if (searchForEvent(user.getEventInvitations(), eventId) != -1)
			user.eventStatus = 1 ;
		else if (searchForEvent(user.getEvents(), eventId) != -1)  
			user.eventStatus = 2 ;
		else
			user.eventStatus = 3 ;
		return user.eventStatus;
	}
	
	@SuppressWarnings("deprecation")
	public static int stateOfEvent(Event event) { //Personal
		Timestamp time = Timestamp.valueOf(event.getDeadline().replace("T", " ")) ;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		if (timestamp.getHours()+6 > 24)
			timestamp.setHours((timestamp.getHours()+6)-24);
		else 
			timestamp.setHours(timestamp.getHours()+6);
		if (time.after(timestamp)) 
			event.eventState = 1 ;
		else 
			event.eventState = 2 ;
		return event.eventState;
	}
}
