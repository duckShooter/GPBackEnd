package com.models.notifications;

import javax.persistence.Entity;

import org.json.simple.JSONObject;

import com.models.event.EventController;
import com.models.user.Profile;

@Entity
public class EventDeletionNotification extends Notification {
	private String eventName;
	
	public EventDeletionNotification() {
		//Empty
	}

	public EventDeletionNotification(Profile owner, String eventName) {
		super(owner);
		this.eventName = eventName;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJsonObject() {
		JSONObject jsonString = new JSONObject();
		
		JSONObject target = new JSONObject();
		target.put("name", eventName);
		
		jsonString.put("owner_id", owner.getUser_Id());
		jsonString.put("target", target);
		jsonString.put("type", NotificationType.EVENT_DELETION);
		jsonString.put("timestamp", timestamp);
		jsonString.put("read", marked);
		return jsonString;
	}
}
