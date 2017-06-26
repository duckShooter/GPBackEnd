package com.models.notifications;

import java.util.List;

import org.hibernate.Session;

import com.services.HibernateUtility;

public class NotificationController {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Notification> fetchUnread(int userID) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		List notifications = 
				(List<Notification>)session.createQuery("FROM Notification n WHERE n.owner.id = :owner_id AND n.marked = false")
		.setParameter("owner_id", userID).list();
		session.close();
		return notifications;
	}
	
	public List<Notification> fetchN(int userID, int n) {
		return null;
	}
	
	public void markAsRead(int userID, int notificationID) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction();
		Notification notification = (Notification)session.get(Notification.class, new Integer(notificationID));
		notification.setMarked(true);
		session.getTransaction().commit();
		session.close();
	}
}
