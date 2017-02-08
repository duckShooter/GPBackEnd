package com.models.location;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Andrew
 * @version 1.0
 * @since 25/1/2017
 */

@Entity
public class Location {
	@Id
	@GeneratedValue
	private int location_id ;
    private double longitude;
    private double latitude;
    private String name;

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
}
