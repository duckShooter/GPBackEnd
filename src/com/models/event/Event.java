package com.models.event;

import com.models.location.Area;
import com.models.user.Profile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.boon.json.annotations.JsonIgnore;


@Entity
public class Event  implements Comparable <Event>{
	@Id
	@GeneratedValue
	private int event_id;
    private String name;
    private String description;

	/*@OneToOne
	@JoinColumn(name = "location_id")
    private Location location;*/
    //private double radius;
    
	@OneToOne
	@JoinColumn(name = "area_id")
    private Area area  ;
    
    private String dateOfEvent;
    private String deadline; //what is this ??
    
    @JsonIgnore
	@ManyToOne
	@JoinColumn(name = "owner_id")
    private Profile owner ;
	
    @JsonIgnore
	@ManyToMany
	@JoinTable(name = "profile_event", joinColumns = {
			@JoinColumn(name = "event_id", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "user_id",
					nullable = false, updatable = false) })
    private List<Profile> users = new ArrayList <Profile> ();
    
    @JsonIgnore
	@ManyToMany
	@JoinTable(name = "Event_Invetations", joinColumns = {
			@JoinColumn(name = "eventId", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "userto",
					nullable = false, updatable = false) })
    private List <Profile> invitations = new ArrayList <Profile> () ;

    //owner or participant 
    @Transient
    public int userStatus = 0 ;
    
    @Transient
    public int eventState = 0 ;

    @JsonIgnore
	@OneToMany (mappedBy = "event")
    List <Suggestion> suggestions = new ArrayList<Suggestion> () ;
    
    
    public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
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

   /* public Location getLocation() {
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
    }*/
    
   

    public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
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
    
	@Override
	public int compareTo(Event event) {
		String d1 = deadline ;
		String d2 = event.deadline ;
	    if (d1.contains("T") ) {
	    	d1 = d1.replace("T", " ") ;
	    }
	    if (d2.contains("T") ) {
	    	d2 = d2.replace("T", " ") ;
	    }
	    //System.out.println(deadline);
		Timestamp time1 = Timestamp.valueOf(d1) ;
		Timestamp time2 = Timestamp.valueOf(d2) ;
		return time1.compareTo(time2) ;
	}

	@JsonIgnore
	public List<Profile> getInvitations() {
		return invitations;
	}

	@JsonIgnore
	public void setInvitations(List<Profile> invitations) {
		this.invitations = invitations;
	}

	@JsonIgnore
	public List<Suggestion> getSuggestions() {
		return suggestions;
	}

	@JsonIgnore
	public void setSuggestions(List<Suggestion> suggestions) {
		this.suggestions = suggestions;
	}
	
	
}
