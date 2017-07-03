package com.models.notifications;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.simple.JSONObject;

import com.models.event.Event;
import com.models.event.EventController;
import com.models.user.Profile;

@Entity
public class EventInvitationNotification extends Notification {
	@ManyToOne(optional = false, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "event_id")
	private Event event;
	
	public EventInvitationNotification() {
		//Empty
	}

	public EventInvitationNotification(Profile owner, Event event) {
		super(owner);
		this.event = event;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJsonObject() {
		JSONObject jsonString = new JSONObject();
		
		JSONObject areaLocation = new JSONObject();
		areaLocation.put("location_id", event.getArea().getLocation().getLocation_id());
		areaLocation.put("name", event.getArea().getLocation().getName());
		areaLocation.put("latitude", event.getArea().getLocation().getLatitude());
		areaLocation.put("longitude", event.getArea().getLocation().getLongitude());
		
		JSONObject eventArea = new JSONObject();
		eventArea.put("area_id", event.getArea().getArea_id());
		eventArea.put("radius", event.getArea().getRedius());
		eventArea.put("area_id", event.getArea().getArea_id());
		eventArea.put("location", areaLocation);
		eventArea.put("image",event.getArea().getImage() ) ;
		
		JSONObject target = new JSONObject();
		target.put("event_id", event.getEvent_id());
		target.put("name", event.getName());
		target.put("area", eventArea);
		target.put("description", event.getDescription());
		target.put("dateOfEvent", event.getDateOfEvent());
		target.put("deadline", event.getDeadline());
		target.put("eventStatus", EventController.userStatusForEvent(owner, event.getEvent_id()));
		target.put("eventState", EventController.stateOfEvent(event));
		
		jsonString.put("id", id);
		jsonString.put("owner_id", owner.getUser_Id());
		jsonString.put("target", target);
		jsonString.put("type", NotificationType.EVENT_INVITATION.toString());
		jsonString.put("timestamp", timestamp.getTime());
		jsonString.put("read", marked);
		return jsonString;
	}
}
