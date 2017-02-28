package com.models.user;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.hibernate.Session;
import com.models.event.Event;
import com.models.location.Area;
import com.models.location.Location;
import com.models.location.LocationController;
import com.services.HibernateUtility;


public class UserController {
	
	public static Profile addUser (String firstName , String lastName , String email , 
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
		
		return profile ;
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
	
	public static List <Profile> getFriends ( int userId ) {
		Profile user = getUser(userId) ;
		return user.getFriends() ;
	}
	
	public static List <Event> getEvents ( int userId ) {
		Profile user = getUser(userId) ;
		return user.getEvents() ;
	}
	
	public static List <Event> getEventsWhoOwn ( int userId) {
		Profile user = getUser(userId) ;
		return user.getEventsWhoOwn();
	}
	
	public static List <Area> getAreas ( int userId ) {
		Profile user = getUser(userId) ;
		return user.getAreas() ;
	}
	
	public static List <Area> getAreasWhoOwn ( int userId ) {
		Profile user = getUser(userId) ;
		return user.getAreasWhoOwn() ;
	}
	
	

	public static double areaOfTriangle(double xa,double ya, double xb, double yb, double px, double py)
	{
	    double side1 = Math.sqrt(Math.pow(Math.abs(ya-yb),2) + Math.pow(Math.abs(xa-xb),2));
	    double side2 = Math.sqrt(Math.pow(Math.abs(ya-py),2) + Math.pow(Math.abs(xa-px),2));
	    double side3 = Math.sqrt(Math.pow(Math.abs(yb-py),2) + Math.pow(Math.abs(xb-px),2));

	    double semi_perimeter = (side1+side2+side3)/2;

	    double area = Math.sqrt(semi_perimeter*(semi_perimeter-side1)*(semi_perimeter-side2)*(semi_perimeter-side3));

	    return area;
	}


	public static double areaOfRect(double x1, double y1,
			double x2, double y2,
			double x3, double y3,
			double x4, double y4)
	{

	    double side1 = Math.sqrt(Math.pow(Math.abs(y1-y2),2) + Math.pow(Math.abs(x1-x2),2));
	    double side2 = Math.sqrt(Math.pow(Math.abs(y2-y3),2) + Math.pow(Math.abs(x2-x3),2));

	    double area = side1*side2;

	    return area;

	}



	public static boolean check(double x1, double y1,
			double x2, double y2,
			double x3, double y3,
			double x4, double y4, 
			double pointX, double pointY)
	{   

	    double trinagle1Area = areaOfTriangle(x1, y1, x2, y2, pointX, pointY);
	    double trinagle2Area = areaOfTriangle(x2, y2, x3, y3, pointX, pointY);
	    double trinagle3Area = areaOfTriangle(x3, y3, x4, y4, pointX, pointY);
	    double trinagle4Area = areaOfTriangle(x4, y4, x1, y1, pointX, pointY);



	    double rectArea = areaOfRect(x1, y1, x2, y2, x3, y3, x4, y4);

	    double triangleAreaSum = (trinagle1Area+trinagle2Area+trinagle3Area+trinagle4Area);
	    System.out.println(triangleAreaSum) ;
	    System.out.println(rectArea) ;

	    if(triangleAreaSum<=rectArea)
	        return true;
	    else
	        return false;
	}
	
	public static List <Profile> getFriendsOnMap (int userId ,double lon1 ,double lat1 ,double lon2 ,double lat2, double lon3 , double lat3 , double lon4 , double lat4) {
		Profile profile = getUser(userId) ;
		List <Profile> friends = profile.getFriends() ;
		List <Profile> closestFriends = new ArrayList <Profile> () ;
		for ( int i = 0 ; i < friends.size() ; i++ ) {
			Location location = getUserLastLocation (friends.get(i).getUser_Id());
			if (location == null ) continue ;
			if (check(lon1,lat1,lon2,lat2,lon3,lat3,lon4,lat4,location.getLongitude(),location.getLatitude()) == true ){
				closestFriends.add(friends.get(i)) ;
			}	
		}
		return closestFriends ;
	}
	
	public static void addUserLocation (int userId , int locationId , String time) {
		Profile user = getUser(userId) ;
		Location location = LocationController.getLocation(locationId) ;
	    Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
	    if ( user.getHistory() == null ) {
	    	History history = new History () ;
	    	user.setHistory(history);
	    	Map <String,Location> positions = new HashMap <String, Location> () ;
	    	positions.put(time, location) ;
	    	user.getHistory().setPositions(positions);
	    }
	    else {
	    	user.getHistory().getPositions().put(time, location) ;
	    }
		session.update(user);
		session.getTransaction().commit();
		session.close();
	}
	
	public static Location getUserLastLocation ( int userId ) {
		Profile user = getUser(userId) ;
		if (user.getHistory() == null ) return null ;
		Map <String,Location> map = user.getHistory().getPositions() ;
		Entry<String, Location> entry=map.entrySet().iterator().next();
		String time = entry.getKey() ;
		Location location = entry.getValue() ;
		return location ;
	}
	

	public static List <Event> getNearbyEvents (int userId) {
		Profile user = getUser(userId) ;
		List <Event> events = user.getEvents() ;
		Collections.sort(events);
		int size = 5 ;
		if ( events.size() < 5 )
			size = events.size() ;
		return events.subList(0,size) ;
	}
	
}
