package com.models.event;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.boon.json.annotations.JsonIgnore;

import com.models.user.Profile;

@Entity
public class Suggestion {
  
	@Id
	@GeneratedValue
	int suggestion_id ;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "event_id")
	Event event ;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	Profile profile ;
	
	String time ;
	
	public int getSuggestion_id() {
		return suggestion_id;
	}
	
	public void setSuggestion_id(int suggestion_id) {
		this.suggestion_id = suggestion_id;
	}
	
	public Event getEvent() {
		return event;
	}
	
	public void setEvent(Event event) {
		this.event = event;
	}
	
	public Profile getProfile() {
		return profile;
	}
	
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
}
