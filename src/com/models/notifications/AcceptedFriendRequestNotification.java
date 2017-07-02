package com.models.notifications;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.simple.JSONObject;

import com.models.user.Profile;
import com.models.user.UserController;

@Entity
public class AcceptedFriendRequestNotification extends Notification {
	@ManyToOne(optional = false, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "acceptor_id")
	private Profile acceptorProfile;
	
	public AcceptedFriendRequestNotification() {
		//Empty
	}

	public AcceptedFriendRequestNotification(Profile owner, Profile acceptorProfile) {
		super(owner);
		this.acceptorProfile = acceptorProfile;
	}

	public Profile getAcceptorProfile() {
		return acceptorProfile;
	}

	public void setAcceptorProfile(Profile acceptorProfile) {
		this.acceptorProfile = acceptorProfile;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJsonObject() {
		JSONObject jsonString = new JSONObject();
		
		JSONObject target = new JSONObject();
		target.put("id", acceptorProfile.getUser_Id());
		target.put("firstName", acceptorProfile.getFirstName());
		target.put("lastName", acceptorProfile.getLastName());
		target.put("email", acceptorProfile.getEmail());
		target.put("homeTown", acceptorProfile.getHomeTown());
		target.put("birthday", acceptorProfile.getBirthday());
		target.put("pictureURL", acceptorProfile.getPictureURL());
		target.put("friendState", UserController.getUserDetails(
				this.owner.getUser_Id(), acceptorProfile.getUser_Id()).friendShipStatus);
		
		jsonString.put("id", id);
		jsonString.put("owner_id", owner.getUser_Id());
		jsonString.put("target", target);
		jsonString.put("type", NotificationType.ACCEPTED_FRIEND_REQUEST.toString());
		jsonString.put("timestamp", timestamp.getTime());
		jsonString.put("read", marked);
		return jsonString;
	}
}
