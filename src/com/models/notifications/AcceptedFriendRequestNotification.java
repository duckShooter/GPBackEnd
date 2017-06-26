package com.models.notifications;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.models.user.Profile;

@Entity
public class AcceptedFriendRequestNotification extends Notification {
	@ManyToOne(optional = false)
	@JoinColumn(name = "acceptor_id")
	private Profile acceptorProfile;
	
	public AcceptedFriendRequestNotification() {
		//Empty
	}

	public AcceptedFriendRequestNotification(Date timestamp, Profile owner, Profile acceptorProfile) {
		super(timestamp, owner);
		this.acceptorProfile = acceptorProfile;
	}

	public Profile getAcceptorProfile() {
		return acceptorProfile;
	}

	public void setAcceptorProfile(Profile acceptorProfile) {
		this.acceptorProfile = acceptorProfile;
	}
}
