package com.models.notifications;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.simple.JSONObject;

import com.models.event.Event;
import com.models.user.Profile;

@Entity
public class EventArrivalNotification extends Notification {
	@ManyToOne(optional = false)
	@JoinColumn(name = "event_id")
	private Event event;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private Profile arrivedUser;
	
	public EventArrivalNotification() {
		//Empty
	}
	
	public EventArrivalNotification(Profile owner, Event event, Profile arrivedUser) {
		super(owner);
		this.event = event;
		this.arrivedUser = arrivedUser;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Profile getArrivedUser() {
		return arrivedUser;
	}

	public void setArrivedUser(Profile arrivedUser) {
		this.arrivedUser = arrivedUser;
	}
	
	@Override
	public JSONObject toJsonObject() {
		// TODO Commit Suicide
		return super.toJsonObject();
	}
}
