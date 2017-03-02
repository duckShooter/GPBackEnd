package com.services;

import java.util.List;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.json.simple.JSONObject;

import com.models.event.Event;
import com.models.event.EventController;
import com.models.location.Area;
import com.models.location.Location;
import com.models.location.LocationController;
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
	@Path("/signup")
	@Produces(MediaType.TEXT_PLAIN)
	public String SignUp(@FormParam("firstname") String firstname ,@FormParam("lastname") String lastname,@FormParam("email") String email,
			@FormParam("hometown") String hometown,@FormParam("name") String name,
			@FormParam("birthday") String birthday,@FormParam("pictureURL") String pictureURL,
			@FormParam("pass") String pass,@FormParam("type") String type) {
		
		Profile profile = UserController.addUser(firstname, lastname, email, hometown, name, birthday, pictureURL, pass, type);
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(profile);
		return jsonString ;
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
			, @FormParam("imageurl") String imageURL , @FormParam("state") boolean state ,@FormParam("locationid") int locationId  ) {
		Event event = EventController.createEvent ( name ,  description ,  radius 
				,  userId ,  dateOfEvent ,   deadline , imageURL ,  state ,  locationId ) ; 
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
	
	@POST
	@Path("/addusertoevent")
	@Produces(MediaType.TEXT_PLAIN)
	public String addUserToEvent (@FormParam("userid")int userId , @FormParam("eventid")int eventId) {
		EventController.addUserToEvent(userId, eventId);
		JSONObject json = new JSONObject();
		json.put("operation", "Done");
		return json.toJSONString();
	}
	
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
			,@FormParam("redius")double redius) {
		Area area = LocationController.createArea(redius, ownerId, locationId) ;
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(area);
		return jsonString ;
		
	}
	
	@POST
	@Path("/addusertoarea")
	@Produces(MediaType.TEXT_PLAIN)
	public String addUserToArea (@FormParam("userid")int userId,@FormParam("areaid")int areaId) {
	  LocationController.addUserToArae(userId, areaId);
	  JSONObject json = new JSONObject();
	  json.put("operation", "Done");
	  return json.toJSONString();
	}
	
	@POST
	@Path("/getuserareas")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUserAreas (@FormParam("userid")int userId) {
	  ObjectMapper mapper = JsonFactory.create();
	  String jsonString = mapper.toJson(UserController.getAreas(userId));
	  return jsonString ;
	}
	
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
	@Path("/geteventowner")
	@Produces(MediaType.TEXT_PLAIN)
	public String getEventOwner (@FormParam("eventid")int eventId) {
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.toJson(EventController.getEventOwner(eventId));
		return jsonString ;
	}
	
	
	
	
	
	
	
	
	
	
}
