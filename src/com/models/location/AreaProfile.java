package com.models.location;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.boon.json.annotations.JsonIgnore;

import com.models.user.Profile;

@Entity
@Table(name = "area_profile")
public class AreaProfile {
	@JsonIgnore
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")  
	private Profile profile;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "area_id")  
	private Area area;

	@JsonIgnore
	private boolean inArea;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public boolean isInArea() {
		return inArea;
	}
	public void setInArea(boolean inArea) {
		this.inArea = inArea;
	}
}