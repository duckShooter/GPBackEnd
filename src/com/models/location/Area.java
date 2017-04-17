package com.models.location;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.boon.json.annotations.JsonIgnore;

import com.models.user.Profile;

@Entity
public class Area {
	
	@Id
	@GeneratedValue
	private int area_id ;
	private double redius ;
	
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "User_Id", nullable = false)
	private Profile owner ;
	
	
	@ManyToMany
	@JoinTable(name = "area_profile", joinColumns = {
			@JoinColumn(name = "area_id", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "user_id",
					nullable = false, updatable = false) })
	private List <Profile> users = new ArrayList <Profile> () ;
	
	@ManyToOne
	@JoinColumn(name = "location_id", nullable = false)
	private Location location ;
	
	private String image ;
	
	
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}
	
	public double getRedius() {
		return redius;
	}
	public void setRedius(double redius) {
		this.redius = redius;
	}
	public Profile getOwner() {
		return owner;
	}
	public void setOwner(Profile owner) {
		this.owner = owner;
	}
	
	public List<Profile> getUsers() {
		return users;
	}
	
	public void setUsers(List<Profile> users) {
		this.users = users;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}	
	
	

}
