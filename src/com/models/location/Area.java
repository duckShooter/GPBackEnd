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
import javax.persistence.OneToMany;
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
	
	/*@ManyToMany
	@JoinTable(name = "area_profile", joinColumns = {
			@JoinColumn(name = "area_id", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "user_id",
					nullable = false, updatable = false) })
	private List <Profile> users = new ArrayList <Profile> () ;*/
	
	@OneToMany(mappedBy = "area",cascade = CascadeType.ALL)
<<<<<<< HEAD
	private List <AreaProfile> users = new ArrayList <AreaProfile> () ;
=======
	private List <area_profile> users = new ArrayList <area_profile> () ;
>>>>>>> 65ac2cd2c58c34a9fdd72aac1248f8e58e62f123
	
	@ManyToOne
	@JoinColumn(name = "location_id", nullable = false)
	private Location location;
	
	private String image;
	
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
	
<<<<<<< HEAD
	public List<AreaProfile> getUsers() {
		return users;
	}
	
	public void setUsers(List<AreaProfile> users) {
=======
	public List<area_profile> getUsers() {
		return users;
	}
	
	public void setUsers(List<area_profile> users) {
>>>>>>> 65ac2cd2c58c34a9fdd72aac1248f8e58e62f123
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
	
	public boolean containsLocation(double latitude, double longitude) { //Tested
		final double earthRadius = 6371.0 * 1000; //Meters
		double deltaLat = Math.toRadians(latitude - location.getLatitude()); //Degrees to radians
		double deltaLon = Math.toRadians(longitude - location.getLongitude());
		double tempA = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) + Math.cos(Math.toRadians(latitude)) 
						* Math.cos(Math.toRadians(location.getLatitude())) * Math.sin(deltaLon/2) * Math.sin(deltaLon/2);
		double tempC = 2 * Math.atan2(Math.sqrt(tempA), Math.sqrt(1.0 - tempA));
		double distance = earthRadius * tempC;						
		return distance <= redius;
	}
}
