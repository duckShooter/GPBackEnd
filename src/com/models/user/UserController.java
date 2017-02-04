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

}
