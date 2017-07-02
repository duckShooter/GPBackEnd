package com.models.notifications;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.simple.JSONObject;

import com.models.user.Profile;
import com.models.user.UserController;

@Entity
public class FriendRequestNotification extends Notification {
	@ManyToOne(optional = false, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "sender_id")
	private Profile senderProfile;

	public FriendRequestNotification() {
		//Empty
	}
	
	public FriendRequestNotification(Date timestamp, Profile owner, Profile senderProfile) {
		super(owner);
		this.senderProfile = senderProfile;
	}
	
	public Profile getSenderProfile() {
		return senderProfile;
	}

	public void setSenderProfile(Profile senderProfile) {
		this.senderProfile = senderProfile;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJsonObject() {
		JSONObject jsonString = new JSONObject();
		
		JSONObject target = new JSONObject();
		target.put("id", senderProfile.getUser_Id());
		target.put("firstName", senderProfile.getFirstName());
		target.put("lastName", senderProfile.getLastName());
		target.put("email", senderProfile.getEmail());
		target.put("homeTown", senderProfile.getHomeTown());
		target.put("birthday", senderProfile.getBirthday());
		target.put("pictureURL", senderProfile.getPictureURL());
		target.put("friendState", UserController.getUserDetails(
				this.owner.getUser_Id(), senderProfile.getUser_Id()).friendShipStatus);
		
		jsonString.put("id", id);
		jsonString.put("owner_id", owner.getUser_Id());
		jsonString.put("target", target);
		jsonString.put("type", NotificationType.FRIEND_REQUEST.toString());
		jsonString.put("timestamp", timestamp.getTime());
		jsonString.put("read", marked);
		return jsonString;
	}
}
