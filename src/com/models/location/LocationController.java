package com.models.location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.models.others.ListObject;
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
	
	public static Area createArea (double redius , int ownerId , int locationId  , String imageURL) {
		Profile owner = UserController.getUser(ownerId) ;
		Location location = getLocation(locationId) ;
		Area area = new Area() ;
		area.setRedius(redius);
		area.setOwner(owner);
		area.setLocation(location);
		area.setImage(imageURL);
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		session.save(area) ;
		session.getTransaction().commit();
		session.close() ;
		
		return area ;
	}
	
	public static Area createAreaWithUsers ( double redius , int ownerId , double lat , double lon , String name , List <Integer> usersId  , String imageURL) {
		Location location = createLocation(lon, lat , name) ;
		Area area = createArea(redius, ownerId, location.getLocation_id(), imageURL) ;
		for ( int i = 0 ; i < usersId.size() ; i++ ) {
			addUserToArae(usersId.get(i) , area.getArea_id());
		}
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
<<<<<<< HEAD
		AreaProfile obj = new AreaProfile () ;
		obj.setArea(area);
		obj.setProfile(user);
		obj.setInArea(true);
=======
		area_profile obj = new area_profile () ;
		obj.setArea(area);
		obj.setProfile(user);
		obj.setState(true);
>>>>>>> 65ac2cd2c58c34a9fdd72aac1248f8e58e62f123
		(area.getUsers()).add(obj);
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		session.update(area);
		session.getTransaction().commit();
		session.close();
	}
	
	public static List <Profile> getAreaUsers (int areaId) {
		Area area = getArae(areaId) ;
<<<<<<< HEAD
		List <AreaProfile> area_profiles = area.getUsers() ;
=======
		List <area_profile> area_profiles = area.getUsers() ;
>>>>>>> 65ac2cd2c58c34a9fdd72aac1248f8e58e62f123
		List <Profile> users = new ArrayList <Profile> () ;
		for ( int i = 0 ; i < area_profiles.size() ; i++ ) {
			users.add(area_profiles.get(i).getProfile());
		}
		return users ;
	}
	
	public static JSONArray getAreaUsersIdAndLocation (int areaId) {
		Area area = getArae(areaId) ;
		List <String> returnvalue = new ArrayList <String> () ;
<<<<<<< HEAD
		List <AreaProfile> users = area.getUsers() ;
=======
		List <area_profile> users = area.getUsers() ;
>>>>>>> 65ac2cd2c58c34a9fdd72aac1248f8e58e62f123
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray() ;
		List <String> result = new ArrayList <String> () ;
		for ( int i = 0 ; i < users.size() ; i++ ) {
			System.out.println(users.size());
			Entry entry = UserController.getUserLastLocationAndTime(users.get(i).getProfile().getUser_Id()) ;
			Location location = (Location) entry.getValue();
			obj.put("id",users.get(i).getProfile().getUser_Id()) ;
			obj.put("latitude",location.getLatitude() ) ;
			obj.put("longitude", location.getLongitude()) ;
			obj.put("time", entry.getKey()) ;
			array.add(obj);	
		}
		
		return array;
	}
	
	public static Profile getAreaOwner (int areaId ){
		Area area = getArae(areaId) ;
		return area.getOwner() ;
	}
	
	public static void deleteArea (int areaId ) {
		Area area = getArae(areaId) ;
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		session.delete(area);
		session.getTransaction().commit();
		session.close();
	}

}