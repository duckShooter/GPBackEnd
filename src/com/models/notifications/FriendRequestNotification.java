package com.models.notifications;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.models.user.Profile;

@Entity
public class FriendRequestNotification extends Notification {
	@ManyToOne(optional = false)
	@JoinColumn(name = "sender_id")
	private Profile senderProfile;

	public FriendRequestNotification() {
		//Empty
	}
	
	public FriendRequestNotification(Date timestamp, Profile owner, Profile senderProfile) {
		super(timestamp, owner);
		this.senderProfile = senderProfile;
	}
	
	public Profile getSenderProfile() {
		return senderProfile;
	}

	public void setSenderProfile(Profile senderProfile) {
		this.senderProfile = senderProfile;
	}
}
