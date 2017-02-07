package com.models.user;

import java.util.Date;
import java.util.Set;

import org.hibernate.Session;

import com.services.HibernateUtility;

public class UserController {
	
	public static void addUser (String firstName , String lastName , String email , 
			String homeTown , String name , String birthday , String pictureURL , 
			String password , String type ) {
		
		Profile profile = new Profile() ; //Set up a Profile object
		profile.setFirstName(firstName);
		profile.setLastName(lastName);
		profile.setEmail(email);
		profile.setHomeTown(homeTown);
		profile.setName(name);
		profile.setPictureURL(pictureURL);
		profile.setBirthday(birthday);
		
		Account account = new Account(); //Set up an Account object
		account.setPassword(password);
		account.setType(type);
		profile.setAccount(account);
		
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		session.save(profile);
		session.getTransaction().commit();
		session.close();
	}
	
	public static Profile getUser (int userId) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		Profile profile = (Profile)session.get(Profile.class, userId) ;
		session.getTransaction().commit();
		session.close() ;
		return profile ;
	}
	
	public static void addFriend (int userId, int friendId) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		Profile user = getUser(userId) ;
		Profile friend = getUser(friendId) ;
		(user.getFriends()).add(friend) ;
		(friend.getFriends()).add(user) ;
		session.update(user);
		session.update(friend);
		session.getTransaction().commit();
		session.close() ;
	}
	
	public static Set <Profile> getFriends ( int userId ) {
		Profile user = getUser(userId) ;
		return user.getFriends() ;
	}

}
