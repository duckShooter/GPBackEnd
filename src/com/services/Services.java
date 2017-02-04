package com.services;

import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.json.simple.JSONObject;
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
		
		UserController.AddUser(firstname, lastname, email, hometown, name, birthday, pictureURL, pass, type);
		
		
		JSONObject json = new JSONObject();
		json.put("operation", "Done");
		return json.toJSONString();
	}
	



}
