package com.models.notifications;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.models.event.Event;
import com.models.event.Suggestion;
import com.models.location.AreaProfile;
import com.models.user.Profile;
import com.services.HibernateUtility;

public class NotificationController {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Notification> fetchUnread(int userID) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		List notifications = 
				(List<Notification>)session.createQuery("FROM Notification n WHERE n.owner.user_Id = :owner_id AND n.marked = false")
		.setParameter("owner_id", userID).list();
		session.close();
		return notifications;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Notification> fetchLastN(int userID, int limit) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		List notifications = 
				(List<Notification>)session.createQuery("FROM Notification n WHERE n.owner.user_Id = :owner_id order by n.id desc")
		.setParameter("owner_id", userID).setMaxResults(limit).list();
		session.close();
		if(notifications == null)
			return new ArrayList<>();
		return notifications;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Notification> fetchAllUntil(int userID, int notificationID) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		List notifications = 
				(List<Notification>)session.createQuery("FROM Notification n WHERE n.owner.user_Id = :owner_id AND n.id > :notid")
		.setParameter("owner_id", userID).setParameter("notid", notificationID).list();
		session.close();
		if(notifications == null)
			return new ArrayList<>();
		return notifications;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Notification> fetchNFrom(int userID, int notificationID, int limit) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		List notifications = 
				(List<Notification>)session.createQuery("FROM Notification n WHERE n.owner.user_Id = :owner_id AND n.id < :notid")
		.setParameter("owner_id", userID).setParameter("notid", notificationID).setMaxResults(limit).list();
		session.close();
		if(notifications == null)
			return new ArrayList<>();
		return notifications;
	}
	
	
	public static void markAsRead(int notificationID) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction();
		Notification notification = (Notification)session.get(Notification.class, notificationID);
		notification.setMarked(true);
		session.getTransaction().commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public static void invokeLocationChange(Profile user, double lat, double lon) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		System.out.println("USER ID IS: " + user.getUser_Id());
		int userid = user.getUser_Id();
		List<AreaProfile> areas = (List<AreaProfile>)session.createQuery("FROM AreaProfile ap WHERE ap.profile.user_Id = :user_id")
				.setParameter("user_id", userid).list();
		
		/* List<Event> events = (List<Event>)session.createQuery("From Event e JOIN e.users u WHERE u.user_id = :user_id")
				.setParameter("user_id", user.getUser_Id()).list(); */
		
		session.beginTransaction();
		for (AreaProfile ap : areas) {
			if(ap.getArea().containsLocation(lat, lon) && !ap.isInArea()) {
				session.persist(new AreaEnteredNotification(ap.getArea().getOwner(), ap.getArea(), user));
				ap.setInArea(true);
				session.update(ap);
			} else if(!ap.getArea().containsLocation(lat, lon) && ap.isInArea()) {
				session.persist(new AreaLeftNotification(ap.getArea().getOwner(), ap.getArea(), user));
				ap.setInArea(false);
				session.update(ap);
			}
		}
		session.getTransaction().commit();
		session.close();
	}
	
	public static void notifyEventDeletion(Event event) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction();
		for(Profile user : event.getUsers())
			session.persist(new EventDeletionNotification(user, event.getName()));
		session.getTransaction().commit();
		session.close();
	}
	
	public static void notifyEventEditing(Event event) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction();
		for(Profile user : event.getUsers())
			session.persist(new EventEditingNotification(user, event));
		session.getTransaction().commit();
		session.close();
	}
}
