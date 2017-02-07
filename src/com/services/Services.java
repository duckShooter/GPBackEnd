package com.services;

import java.util.Date;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.hibernate.Session;
import org.json.simple.JSONObject;

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
		
		UserController.addUser(firstname, lastname, email, hometown, name, birthday, pictureURL, pass, type);
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
	
	
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/getfriends")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetFriends(@FormParam("userid") int userId) {
		Set <Profile> friends = UserController.getFriends(userId);
		ObjectMapper mapper = JsonFactory.create();
		String jsonString = mapper.writeValueAsString(friends);
		return jsonString ;
	}
	
	



}
