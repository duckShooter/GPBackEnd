package com.models.location;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.boon.json.annotations.JsonIgnore;

@Entity
public class Location {
	@Id
	@GeneratedValue
	private int location_id ;
    private double longitude;
    private double latitude;
    private String name;
    
    @JsonIgnore
   // @OneToOne(mappedBy = "location")
    @OneToMany (mappedBy="location")
    private List <Area> areas = new ArrayList <Area> () ;

    public Location(){
        this.longitude = 0.0;
        this.latitude = 0.0;
        this.name = null;
    }
    
    public Location(double longitude, double latitude, String name){
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
    }
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<Area> getAreas() {
		return areas;
	}

	@JsonIgnore
	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}
	
	public int getLocation_id() {
		return location_id;
	}

	public void setLocation_id(int location_id) {
		this.location_id = location_id;
	}
    
}
