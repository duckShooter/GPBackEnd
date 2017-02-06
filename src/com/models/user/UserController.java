package com.models.user;

import java.util.Date;

import org.hibernate.Session;

import com.services.DBConnection;

public class UserController {
	
	public static void AddUser (String firstName , String lastName , String email , 
			String homeTown , String name , String birthday , String pictureURL , 
			String password , String type ) {
		
		Profile profile = new Profile() ;
		profile.setFirstName(firstName);
		profile.setLastName(lastName);
		profile.setEmail(email);
		profile.setHomeTown(homeTown);
		profile.setName(name);
		profile.setPictureURL(pictureURL);
		profile.setBirthday(birthday);
		Account account = new Account() ;
		account.setPassword(password);
		account.setType(type);
		profile.setAccount(account);
		Session session = DBConnection.Connection() ;
		session.beginTransaction() ;
		session.save(profile);
		session.getTransaction().commit();
		session.close() ;
		
	}
	
	public static Profile GetUser (int userId) {
		Session session = DBConnection.Connection() ;
		session.beginTransaction() ;
		Profile profile = (Profile)session.get(Profile.class, userId) ;
		session.getTransaction().commit();
		session.close() ;
		return profile ;
	}
	
	public static void AddFriend (int userId , int friendId ) {
		Session session = DBConnection.Connection() ;
		session.beginTransaction() ;
		Profile user = GetUser(userId) ;
		Profile friend = GetUser(friendId) ;
		(user.getFriends()).add(friend) ;
		(friend.getFriends()).add(user) ;
		session.update(user);
		session.update(friend);
		session.getTransaction().commit();
		session.close() ;
	}

}
