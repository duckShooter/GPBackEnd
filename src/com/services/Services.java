package com.services;

import java.sql.Timestamp;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.models.event.Event;
import com.models.event.EventController;
import com.models.location.Area;
import com.models.location.Location;
import com.models.location.LocationController;
import com.models.notifications.Notification;
import com.models.notifications.NotificationController;
import com.models.user.Profile;
import com.models.user.UserController;

@Path("/")
public class Services {

	@SuppressWarnings("unchecked")
	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("email") String email,
			@FormParam("pass") String pass) {
		JSONObject json = new JSONObject();
		json.put("operation", "Done");
		return json.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/addfriend")
	@Produces(MediaType.TEXT_PLAIN)
	public String AddFriend(@FormParam("userid") int userId ,@FormParam("friendid") int friendId) {
		
		UserController.addFriend(userId, friendId);
		JSONObject json = new JSONObject();
		json.put("operation", "Done");
		return json.toJSONString();
	}
	
	@POST
	@Path("/getfriends")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetFriends(@FormParam("userid") int userId) {
		List <Profile> friends = UserController.getFriends(userId);
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.writeValueAsString(friends);
		return jsonString ;
	}
	
	@POST
	@Path("/createevent")
	@Produces(MediaType.TEXT_PLAIN)
	public String createEvent(@FormParam("name")String name , @FormParam("description")String description , @FormParam("radius") double radius 
			, @FormParam("userid") int userId ,@FormParam("dateofevent") String dateOfEvent ,  @FormParam("deadline") String deadline 
		    ,@FormParam("locationid") int locationId , @FormParam("img") String image) {
		Event event = EventController.createEvent ( name ,  description ,  radius 
				,  userId ,  dateOfEvent ,   deadline ,  locationId , image ) ; 
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(event);
		return jsonString ;
	}
		
	@POST
	@Path("/createlocation")
	@Produces(MediaType.TEXT_PLAIN)
	public String createLocation (@FormParam("longitude")double longitude , @FormParam("latitude")double latitude ,@FormParam("name") String name) {
		Location location = LocationController.createLocation ( longitude ,  latitude ,  name);
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(location);
		return jsonString ;
	}
	
	@POST
	@Path("/getownersevent")
	@Produces(MediaType.TEXT_PLAIN)
	public String getOwnersEvent (@FormParam("ownerid")int ownerId) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(UserController.getEventsWhoOwn(ownerId));
		return jsonString ;
	}
	
	// own and participant 
	@POST
	@Path("/getuserevents")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUserEvent (@FormParam("userid")int userId) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(UserController.getEvents(userId));
		return jsonString ;
	}
		
	@POST
	@Path("/createarea")
	@Produces(MediaType.TEXT_PLAIN)
	public String createArea (@FormParam("ownerid")int ownerId,@FormParam("locationid")int locationId
			,@FormParam("redius")double redius , @FormParam("imageurl")String imageurl) {
		Area area = LocationController.createArea(redius, ownerId, locationId , imageurl) ;
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(area);
		return jsonString ;
		
	}
	
	@POST
	@Path("/createareawithusers")
	@Produces(MediaType.TEXT_PLAIN)
	public String createAreaWithUsers (@FormParam("ownerid")int ownerId,@FormParam("lon")double lon , @FormParam("lat")double lat 
			,@FormParam("redius")double redius , @FormParam("name")String name , @FormParam("imageurl")String imageurl , @FormParam("userid")List <Integer> usersId ) {
		Area area = LocationController.createAreaWithUsers(redius, ownerId, lat, lon, name, usersId, imageurl) ;
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(area);
		return jsonString ;	
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/addusertoarea")
	@Produces(MediaType.TEXT_PLAIN)
	public String addUserToArea (@FormParam("userid")int userId,@FormParam("areaid")int areaId) {
	  LocationController.addUserToArae(userId, areaId);
	  JSONObject json = new JSONObject();
	  json.put("operation", "Done");
	  return json.toJSONString();
	}
	
	/*@POST
	@Path("/getuserareas")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUserAreas (@FormParam("userid")int userId) {
	  ObjectMapper mapper = JsonFactory.create();
	  ListObject area = UserController.getAreasWhoOwn(userId) ;
	  String jsonString = mapper.toJson(area);
	  return jsonString ;
	}*/
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/adduserlocation")
	@Produces(MediaType.TEXT_PLAIN)
	public String addUserLocation (@FormParam("userid")int userId , @FormParam("locationid")int locationId
			, @FormParam("time")String time) {
		UserController.addUserLocation(userId, locationId, time);
		 JSONObject json = new JSONObject();
		  json.put("operation", "Done");
		  return json.toJSONString();
	}
	
	@POST
	@Path("/userlastlocation")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUserLastLocation (@FormParam("userid")int userId ) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(UserController.getUserLastLocation(userId));
		return jsonString ;
	}
	
	@POST
	@Path("/getfriendsonmap")
	@Produces(MediaType.TEXT_PLAIN)
	public String getFriendsOnMap (@FormParam("userid")int userId , @FormParam("lat1")double lat1  ,@FormParam("lon1") double lon1 ,@FormParam("lat2")double lat2,@FormParam("lon2") double lon2 ,
			@FormParam("lat3") double lat3 ,@FormParam("lon3") double lon3 ,@FormParam("lat4") double lat4 ,@FormParam("lon4") double lon4)  {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(UserController.getFriendsOnMap(userId,lat1 ,lon1,lat2, lon2, lat3 ,lon3, lat4,lon4));
		return jsonString ;
	}
	
	@POST
	@Path("/getnearbyevents")
	@Produces(MediaType.TEXT_PLAIN)
	public String getNearbyEvents (@FormParam("userid")int userId) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(UserController.getNearbyEvents(userId));
		return jsonString ;
	}
	
	@POST
	@Path("/geteventusers")
	@Produces(MediaType.TEXT_PLAIN)
	public String getEventUsers (@FormParam("eventid")int eventId) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(EventController.getEventUsers(eventId));
		return jsonString ;
	}
	
	@POST
	@Path("/getuserareas")
	@Produces(MediaType.TEXT_PLAIN)
	public String getOwnersAreas (@FormParam("ownerid")int ownerId) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(UserController.getAreasWhoOwn(ownerId));
		return jsonString ;
	}

	@POST
	@Path("/getareausers")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAreaUsers (@FormParam("areaid")int areaId) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(LocationController.getAreaUsers(areaId));
		return jsonString ;
	}
	
	@POST
	@Path("/getareaowner")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAreaOwner (@FormParam("areaid")int areaId) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(LocationController.getAreaOwner(areaId));
		return jsonString ;
	}
	
	@POST
	@Path("/getuserbyid")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUserById (@FormParam("userid")int userId) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(UserController.getUser(userId));
		return jsonString ;
	}
	
	@POST
	@Path("/geteventbyid")
	@Produces(MediaType.TEXT_PLAIN)
	public String getEventById (@FormParam("eventid")int eventId) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(EventController.getEvent(eventId));
		return jsonString ;
	}
	
	@POST
	@Path("/getareabyid")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAreaById (@FormParam("areaid")int areaId) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(LocationController.getArae(areaId));
		return jsonString ;
	}
	
	@POST
	@Path("/getlocationbyid")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLocationById (@FormParam("locationid")int locationId) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(LocationController.getLocation(locationId));
		return jsonString ;
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getareauserswithlocation")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAreaUsersWithLocation (@FormParam("areaid")int areaId ,@FormParam("userid")int userId , @FormParam("pass")String password ) {
		JSONObject obj = new JSONObject();
		obj.put("object", LocationController.getAreaUsersIdAndLocation(areaId))	;	
		return obj.toJSONString() ;
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/acceptfriendrequest")
	@Produces(MediaType.TEXT_PLAIN)
	public String acceptFriendRequest (@FormParam("userid")int userId ,@FormParam("friendid")int friendId) {
		UserController.acceptFriendRequest(userId, friendId);
		JSONObject obj = new JSONObject();
		obj.put("operation", "Done")	;	
		return obj.toJSONString() ;
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/rejectfriendrequest")
	@Produces(MediaType.TEXT_PLAIN)
	public String rejectFriendRequest (@FormParam("userid")int userId ,@FormParam("friendid")int friendId) {
		UserController.rejectFriendRequest(userId, friendId);
		JSONObject obj = new JSONObject();
		obj.put("operation", "Done")	;	
		return obj.toJSONString() ;
	}
	
	@POST
	@Path("/getpendingrequests")
	@Produces(MediaType.TEXT_PLAIN)
	public String getPendingRequests (@FormParam("userid")int userId ) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(UserController.getPendingRequests(userId));
		return jsonString ;
	}

	@POST
	@Path("/getfriendrequests")
	@Produces(MediaType.TEXT_PLAIN)
	public String getFriendRequests (@FormParam("userid")int userId ) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(UserController.getFriendRequests(userId));
		return jsonString ;
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/removefriend")
	@Produces(MediaType.TEXT_PLAIN)
	public String removeFriend (@FormParam("userid")int userId ,@FormParam("friendid")int friendId) {
		UserController.removeFriend(userId, friendId); ;
		JSONObject obj = new JSONObject();
		obj.put("operation", "Done")	;	
		return obj.toJSONString() ;
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/updateuserlocation")
	@Produces(MediaType.TEXT_PLAIN)
	public String updateUserLocation(@FormParam("userid")int userId ,@FormParam("lat")double latitude ,@FormParam("lon")double longitude ) {
		UserController.updateLocation(userId, latitude, longitude);
		JSONObject obj = new JSONObject();
		obj.put("operation", "Done");
		return obj.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/sendinvitation")
	@Produces(MediaType.TEXT_PLAIN)
	public String sendInvitation (@FormParam("friendid")int friendId , @FormParam("eventid")int eventId) {
		EventController.sendInvitation(friendId, eventId);
		JSONObject json = new JSONObject();
		json.put("operation", "Done");
		return json.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/accepteventinvitation")
	@Produces(MediaType.TEXT_PLAIN)
	public String acceptEventInvitation (@FormParam("userid")int userId , @FormParam("eventid")int eventId) {
		UserController.acceptEventInvitation(userId, eventId);
		JSONObject json = new JSONObject();
		json.put("operation", "Done");
		return json.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/rejecteventinvitation")
	@Produces(MediaType.TEXT_PLAIN)
	public String rejectEventInvitation (@FormParam("userid")int userId , @FormParam("eventid")int eventId) {
		UserController.rejectEventInvitation(userId, eventId);
		JSONObject json = new JSONObject();
		json.put("operation", "Done");
		return json.toJSONString();
	}
	
	@POST
	@Path("/listofuserstoinvit")
	@Produces(MediaType.TEXT_PLAIN)
	public String listOfUsersToInvit (@FormParam("userid")int userId , @FormParam("eventid")int eventId) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(EventController.listOfUsersToInvit(userId, eventId));
		return jsonString ;
	}
	
	@POST
	@Path("/getuserdetails")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUserDetails (@FormParam("userid1")int userId1 , @FormParam("userid2")int userId2) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(UserController.getUserDetails(userId1, userId2));
		return jsonString ;
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/deleteevent")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteEvent (@FormParam("eventid")int eventId) {
		EventController.deleteEvent(eventId);
		JSONObject json = new JSONObject();
		json.put("operation", "Done");
		return json.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/addsuggestion")
	@Produces(MediaType.TEXT_PLAIN)
	public String addSuggestion (@FormParam("userid")int userId ,@FormParam("eventid")int eventId ,@FormParam("date")String date ) {
		JSONObject json = new JSONObject();
		json.put("operation", EventController.addSuggestion(userId, eventId, date));
		return json.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/add")
	@Produces(MediaType.TEXT_PLAIN)
	public String add ( ) {
		
		JSONObject json = new JSONObject();
		json.put("operation","Done");
		return json.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/acceptsuggestion")
	@Produces(MediaType.TEXT_PLAIN)
	public String acceptSuggestion (@FormParam("userid")int userId ,@FormParam("eventid")int eventId ,@FormParam("suggestionid")int suggestionId ) {
		
		JSONObject json = new JSONObject();
		json.put("operation", EventController.acceptSuggestion(userId, eventId, suggestionId));
		return json.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/rejectsuggestion")
	@Produces(MediaType.TEXT_PLAIN)
	public String rejectSuggestion (@FormParam("userid")int userId ,@FormParam("eventid")int eventId ,@FormParam("suggestionid")int suggestionId ) {
		
		JSONObject json = new JSONObject();
		json.put("operation", EventController.rejectSuggestion(userId, eventId, suggestionId));
		return json.toJSONString();
	}
	
	@POST
	@Path("/getsuggestions")
	@Produces(MediaType.TEXT_PLAIN)
	public String getSuggestions (@FormParam("eventid")int eventId) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(EventController.getSuggestions(eventId));
		return jsonString ;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@POST
	@Path("/gettimestamp")
	@Produces(MediaType.TEXT_PLAIN)
	public String  getTimeStamp () {
		Timestamp time = new Timestamp(System.currentTimeMillis()) ;
		if (time.getHours()+6 > 24 )
			time.setHours((time.getHours()+6)-24);
		else 
			time.setHours(time.getHours()+6);
		JSONObject json = new JSONObject();
		json.put("Time",time.toString());
		return json.toJSONString();
	}
	
	
	@POST
	@Path("/geteventuserswithlocation")
	@Produces(MediaType.TEXT_PLAIN)
	public String  getEventUsersWithLocation (@FormParam("eventid")int eventId) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(EventController.getEventUsersWithLocation(eventId));
		return jsonString ;
	}
	
	
	
	@POST
	@Path("/checkregiseration")
	@Produces(MediaType.TEXT_PLAIN)
	public String  checkRegiseration (@FormParam("loginid")long loginId , @FormParam("provider")String provider ) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(UserController.checkRegiseration(loginId, provider));
		return jsonString ;
	}
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/register")
	@Produces(MediaType.TEXT_PLAIN)
	public String register(@FormParam("firstname") String firstname ,@FormParam("lastname") String lastname,@FormParam("email") String email,
			@FormParam("hometown") String hometown,@FormParam("name") String name,
			@FormParam("birthday") String birthday,@FormParam("pictureURL") String pictureURL,
			@FormParam("pass") String pass , @FormParam("provider") String provider , @FormParam("loginid") long loginId ) {
		
		Profile profile = UserController.addUser(firstname, lastname, email, hometown, name, birthday, pictureURL, pass, provider , loginId);
		JSONObject json = new JSONObject();
		json.put("id",profile.getUser_Id());
		return json.toJSONString() ;
	}
	
	@POST
	@Path("/updateplaces")
	@Produces(MediaType.TEXT_PLAIN)
	public String register(@FormParam("userid") int userId ,@FormParam("areaid") int areaId) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(UserController.getGreaterAreas(userId, areaId));
		return jsonString ;
	
	}
	
	@POST
	@Path("/getsomeareas")
	@Produces(MediaType.TEXT_PLAIN)
	public String getSomeAreas(@FormParam("userid") int userid ,@FormParam("areaid") int areaid  ) {
		
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(UserController.getSomeAreas(userid, areaid));
		return jsonString ;

	}
	
	
	@POST
	@Path("/searchuserbyname")
	@Produces(MediaType.TEXT_PLAIN)
	public String searchUserByName(@FormParam("name") String name ) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(UserController.searchByName(name));
		return jsonString ;
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/notifications")
	@Produces(MediaType.APPLICATION_JSON)
	public String loadNotifications(@QueryParam("user_id") int userId, @QueryParam("age") byte ageBit,
			@DefaultValue("-1") @QueryParam("notification_id") int notificationId) {
		JSONObject responseEntity = new JSONObject();
		JSONArray notifications = new JSONArray();
		if(notificationId == -1) {
			for(Notification n  : NotificationController.fetchLastN(userId,  10))
				notifications.add(n.toJsonObject());
		} else {
			if(ageBit == 1) { //Request for notifications newer than the specified notification ID
				for(Notification n  : NotificationController.fetchAllUntil(userId, notificationId))
					notifications.add(n.toJsonObject());
				
			} else { //Request for notifications older than the specified notification ID
				for(Notification n :  NotificationController.fetchNFrom(userId, notificationId, 10))
					notifications.add(n.toJsonObject());
			}
		}
		responseEntity.put("notifications", notifications);
		return responseEntity.toJSONString();
	}
	
	@POST
	@Path("/markread")
	public Response markAsRead(@FormParam("notification_id") int notificationId) {
		NotificationController.markAsRead(notificationId);
		return Response.ok().build();
	}
}
