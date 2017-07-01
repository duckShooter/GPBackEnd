package com.models.user;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.models.event.Event;
import com.models.event.EventController;
import com.models.location.Area;
import com.models.location.Location;
import com.models.location.LocationController;
import com.models.notifications.AcceptedFriendRequestNotification;
import com.models.notifications.FriendRequestNotification;
import com.models.notifications.Notification;
import com.models.others.ListObject;
import com.services.HibernateUtility;


public class UserController {
	
	public static Profile addUser (String firstName , String lastName , String email , 
			String homeTown , String name , String birthday , String pictureURL , 
			String password , String provider , long loginId ) {
		
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
		account.setProvider(provider);
		account.setLoginId(loginId);
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
		session.beginTransaction();
		Profile profile = (Profile)session.get(Profile.class, userId);
		session.getTransaction().commit();
		session.close();
		return profile;
	}
	
	public static void addFriend (int userId, int friendId) {
		Profile user = getUser(userId) ;
		Profile friend = getUser(friendId) ;
		if (searchArray(user.getFriends(), friend) != -1 || searchArray(user.getPendingRequests(), friend) != -1 || searchArray(user.getRequestedto(), friend) != -1 )
			return ;
		
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		(user.getRequestedto()).add(friend) ;
		session.update(user);
		session.persist(new FriendRequestNotification(new Date(), friend, user)); //Notification
		session.getTransaction().commit();
		session.close();
	}
	
	public static int searchArray (List <Profile> list , Profile o) {
		for ( int i = 0 ; i < list.size() ; i++ ) {
			if ( list.get(i).getUser_Id() == o.getUser_Id())
				return i ;
		}
		return -1 ;
	}
	
	public static void RemoveFriendFromPending (int userId , int friendId ) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		Profile user = getUser(userId) ;
		Profile friend = getUser(friendId) ;
		if (searchArray(friend.getRequestedto(),user) == -1 )
			return ;
		(friend.getRequestedto()).remove(searchArray(friend.getRequestedto(),user)) ;
		System.out.println((friend.getRequestedto()).size());
		session.update(friend);
		session.getTransaction().commit();
		session.close() ;
		
	}
	
	public static void acceptFriendRequest (int userId , int friendId) {
		RemoveFriendFromPending (userId, friendId) ;
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction();
		Profile user = getUser(userId);
		Profile friend = getUser(friendId);
		(user.getFriends()).add(friend);
		(friend.getFriends()).add(user);
		session.update(user);
		session.update(friend);
		session.persist(new AcceptedFriendRequestNotification(new Date(), friend, user)); //Notification
		session.getTransaction().commit();
		session.close() ;
		
	}
	
	public static void rejectFriendRequest ( int userId ,int friendId) {
		RemoveFriendFromPending (userId, friendId) ;
	}
	
	public static void removeFriend ( int userId , int friendId ) {
		Profile user = getUser(userId) ;
		Profile friend = getUser(friendId) ;
		int index1 = searchArray(user.getFriends(),friend) ;
		if (index1 == -1 )
			return ;
		int index2 = searchArray(friend.getFriends(),user) ;
		if ( index2 == -1 )
			return ;
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		(user.getFriends()).remove(index1) ;
		(friend.getFriends()).remove(index2) ;
		session.update(user);
		session.update(friend);
		session.getTransaction().commit();
		session.close() ;
	}
	
	public static List <Profile> getPendingRequests (int userId ) {
		Profile user = getUser(userId) ;
		return user.getPendingRequests() ;
	}
	
	public static List <Profile> getFriendRequests (int userId) {
		Profile user = getUser(userId) ;
		return user.getRequestedto() ;
	}
	
	public static List <Profile> getFriends ( int userId ) {
		Profile user = getUser(userId) ;
		return user.getFriends() ;
	}
	
	// own and participant 
	public static List <Event> getEvents ( int userId ) {
		Profile user = getUser(userId) ;
		List <Event> events =EventController.setUserStatus(user.getEventsWhoOwn(), 2);
		events.addAll( EventController.setUserStatus(user.getEvents(), 1)) ;
		Collections.sort(events);
		events = EventController.markEventState(events) ;
		return events;
	}
	
	public static List <Event> getEventsWhoOwn ( int userId) {
		Profile user = getUser(userId) ;
		return user.getEventsWhoOwn();
	}
	
	public static ListObject getAreas ( int userId ) {
		Profile user = getUser(userId) ;
		ListObject list = new ListObject (user.getAreas()) ;
		return list;
	}
	
	
	
	public static List <Area> getAreasWhoOwn ( int userId ) {
		Profile user = getUser(userId) ;
		System.out.println("cdscscsdcdsdsdcsdcsd" + user.getAreasWhoOwn().size());
		return user.getAreasWhoOwn() ;
	}
	
	
	public static List <Area> getGreaterAreas (int userId , int areaId) {
		List <Area> areas = getAreasWhoOwn (userId) ;
		List <Area> result = new <Area> ArrayList () ;
		for ( int i = 0 ;  i< areas.size() ; i++ ) {
			if (areas.get(i).getArea_id() > areaId) {
				result.add(areas.get(i)) ;
			}
		}
		return result ;
		
	}
	
	public static double areaOfTriangle(double xa,double ya, double xb, double yb, double px, double py) {
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
			double x4, double y4) {

	    double side1 = Math.sqrt(Math.pow(Math.abs(y1-y2),2) + Math.pow(Math.abs(x1-x2),2));
	    double side2 = Math.sqrt(Math.pow(Math.abs(y2-y3),2) + Math.pow(Math.abs(x2-x3),2));

	    double area = side1*side2;

	    return area;

	}

	public static boolean check(double x1, double y1,
			double x2, double y2,
			double x3, double y3,
			double x4, double y4, 
			double pointX, double pointY) {   

	    double trinagle1Area = areaOfTriangle(x1, y1, x2, y2, pointX, pointY);
	    double trinagle2Area = areaOfTriangle(x2, y2, x3, y3, pointX, pointY);
	    double trinagle3Area = areaOfTriangle(x3, y3, x4, y4, pointX, pointY);
	    double trinagle4Area = areaOfTriangle(x4, y4, x1, y1, pointX, pointY);



	    double rectArea = areaOfRect(x1, y1, x2, y2, x3, y3, x4, y4);

	    double triangleAreaSum = (trinagle1Area+trinagle2Area+trinagle3Area+trinagle4Area);
	    System.out.println(triangleAreaSum) ;
	    System.out.println(rectArea) ;

	    if((int)triangleAreaSum==(int)rectArea)
	        return true;
	    else
	        return false;
	}
	
	public static List <Profile> getFriendsOnMap (int userId ,double lat1 ,double lon1 ,double lat2,double lon2 ,  double lat3 ,double lon3 , double lat4 ,double lon4 ) {
		Profile profile = getUser(userId) ;
		List <Profile> friends = profile.getFriends() ;
		List <Profile> closestFriends = new ArrayList <Profile> () ;
		for ( int i = 0 ; i < friends.size() ; i++ ) {
			Location location = getUserLastLocation (friends.get(i).getUser_Id());
			if (location == null ) continue ;
			if (check(lat1,lon1,lat2,lon2,lat3,lon3,lat4,lon4,location.getLatitude(),location.getLongitude()) == true ){
				friends.get(i).latitude = location.getLatitude() ;
				friends.get(i).longitude = location.getLongitude() ;
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
	    	user.getHistory().getPositions().clear();
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
	
	public static Entry<String, Location>  getUserLastLocationAndTime ( int userId ) {
		Profile user = getUser(userId) ;
		if (user.getHistory() == null ) return null ;
		Map <String,Location> map = user.getHistory().getPositions() ;
		Entry<String, Location> entry=map.entrySet().iterator().next();
		return entry ;
	}
	
	// moreExist = 1 -> <= 5
	// moreExist = 2 -> > 5
	public static ListObject getNearbyEvents (int userId) {
		Profile user = getUser(userId) ;
		List <Event> events =EventController.setUserStatus(user.getEventsWhoOwn(), 2);
		events.addAll( EventController.setUserStatus(user.getEvents(), 1)) ;
		Collections.sort(events);
		List <Event> unfinished = EventController.getUnfinishedEvents(events) ;
		int size = 5 ;
		ListObject obj ;
		if ( unfinished.size() <= 5 ) {
			obj = new ListObject(unfinished) ;
			if ( unfinished.size() >= events.size() ) {
				obj.moreExist = 1 ;
			}
			else obj.moreExist = 2 ;
		}
		else {
			obj = new ListObject(unfinished.subList(0,size));
			obj.moreExist = 2 ;
		}
		
		return obj ;
	}
	
	public static void updateLocation (int userId , double latitude , double longitude )  {
		Location location = LocationController.createLocation(longitude, latitude, "") ;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String time = dateFormat.format(date).toString() ;
		addUserLocation(userId, location.getLocation_id(), time);
	}
	
	public static void acceptEventInvitation (int userId , int eventId ) {
		Profile user = getUser(userId) ;
		int index = EventController.searchForEvent(user.getEventInvitations(),eventId) ;
		if ( index != -1 ) {
			EventController.addUserToEvent(userId, eventId);
			EventController.removeFromInvitationList(eventId, userId);
		}	
	}
	
	public static void rejectEventInvitation (int userId , int eventId) {
		Profile user = getUser(userId) ;
		int index = EventController.searchForEvent(user.getEventInvitations(),eventId) ;
		if ( index != -1 ) {
			EventController.removeFromInvitationList(eventId, userId);
		}
	}
	
	public static Profile getUserDetails (int userId1 , int userId2 ) {
		Profile user1 = getUser(userId1) ;
		Profile user2 = getUser(userId2) ;
		
		if ( searchArray(user1.getFriends(),user2) != -1 ) user1.friendShipStatus = 1 ;
		else if ( searchArray(user1.getPendingRequests(),user2) != -1 ) user1.friendShipStatus = 2 ;
		else if ( searchArray(user1.getRequestedto(),user2) != -1 ) user1.friendShipStatus = 3 ;
		else  user1.friendShipStatus = 4 ;
		
		return user1 ;
		
	}
	
	public static JSONObject checkRegiseration (long loginId , String provider) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		Query query = session.createQuery("from Account where loginId = :id and provider = :provider ");
		query.setParameter("id", loginId );
		query.setParameter("provider", provider );
		List list = query.list();
		session.getTransaction().commit();
		 session.close() ;
		 JSONObject obj = new JSONObject();
		 String isRegisterd ;
		 if (list.size() == 0 ) { 
			 obj.put("isRegisterd","1") ;
			 return obj ;
		 }
		 Account account = (Account) list.get(0) ;
		 obj.put("isRegisterd","2") ;
		 obj.put("id",String.valueOf(account.getUser_Id())) ;
		 obj.put("pass",account.getPassword()) ;
		 Profile user = getUser(account.getUser_Id()) ;
		 obj.put("name",user.getName()) ;
		 
		 return obj ;
	}
	
	
	public static List <Profile> searchByName (String name) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		session.beginTransaction() ;
		Query query = session.createQuery("from Profile where name = :name ");
		query.setParameter("name", name );
		List list = query.list();
		session.getTransaction().commit();
		session.close() ; 
		return list ;
	}	
}
