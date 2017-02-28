package com.models.event;

import com.models.location.Location;
import com.models.user.Profile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
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

import org.boon.json.annotations.JsonIgnore;


@Entity
public class Event  implements Comparable <Event>{
	@Id
	@GeneratedValue
	private int event_id;
    private String name;
    private String description;
    private String imageURL;

	@OneToOne
	@JoinColumn(name = "location_id")
    private Location location;
    private double radius;
    
    private String dateOfEvent;
    private String deadline; //what is this ??
    private boolean state;
    
    @JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "owner_id")
    private Profile owner ;
	
    @JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "profile_event", joinColumns = {
			@JoinColumn(name = "event_id", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "user_id",
					nullable = false, updatable = false) })
    private List<Profile> users = new ArrayList <Profile> ();


    public Event(){
        imageURL = null;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
    public Profile getOwner() {
		return owner;
	}
	public void setOwner(Profile owner) {
		this.owner = owner;
	}
	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @JsonIgnore
    public List<Profile> getUsers() {
        return users;
    }

    @JsonIgnore
    public void setUsers(List<Profile> users) {
        this.users = users;
    }

    public String getDateOfEvent() {
        return dateOfEvent;
    }

    public void setDateOfEvent(String dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
    
	@Override
	public int compareTo(Event event) {
		Timestamp time1 = Timestamp.valueOf(deadline) ;
		Timestamp time2 = Timestamp.valueOf(event.deadline) ;
		return time1.compareTo(time2) ;
	}
	
	/*public static Comparator<Event> EventTimeComparator
     	= new Comparator<Event>() {
			public int compare(Event event1, Event event2) {
			return event1.compareTo(event2);
		}
		};*/
}
