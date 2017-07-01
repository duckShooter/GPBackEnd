package com.models.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;

import org.boon.json.annotations.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.models.event.Event;
import com.models.event.Suggestion;
import com.models.location.Area;
import com.models.location.area_profile;

@Entity
public class Profile {
	
	
	@Id @GeneratedValue(generator = "newGenerator") //name of the primary key generator
	@GenericGenerator(name = "newGenerator", strategy = "foreign",parameters = {@Parameter(value = "account", name = "property") })

	private int user_Id ;

	private String firstName;
    private String lastName;
    private String email;
    private String homeTown;
    private String name;
    //@Temporal (TemporalType.DATE)
    private String birthday;
	private String pictureURL;
	@Transient
	public int eventStatus = 0 ;
	@Transient
	public int friendShipStatus = 0;
	@Transient
	public double latitude = 0.0 ;
	@Transient
	public double longitude = 0.0 ;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_Id")
	private Account account = new Account() ;
	
	@JsonIgnore
	@OneToMany ( mappedBy = "owner")
	private List <Event> eventsWhoOwn = new ArrayList <Event> () ;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "users")
	private List <Event> events = new ArrayList <Event> () ;
	
	
	@ManyToMany
	@JsonIgnore
	private List <Profile> Friends = new ArrayList <Profile> (0) ;

	@JsonIgnore
	@OneToMany (mappedBy = "owner")
    private List <Area> areasWhoOwn = new ArrayList<Area> () ;
	
	/*@JsonIgnore
	@ManyToMany(mappedBy = "users")
	private List <Area> areas = new ArrayList<Area> () ;*/
	
	@JsonIgnore
	@OneToMany(mappedBy = "profile")
	private List <area_profile> areas = new ArrayList <area_profile> () ;
	

	@JsonIgnore
	@OneToOne (cascade = CascadeType.ALL)
	@JoinColumn(name = "history_id")
	private History history = new History() ; 

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Friend_Requests", joinColumns = {
			@JoinColumn(name = "userfrom", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "userto",
					nullable = false, updatable = false) })
	private List <Profile> Requestedto = new ArrayList<Profile>(0) ;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL,mappedBy = "Requestedto")
	private List <Profile> pendingRequests = new ArrayList<Profile>(0) ;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "invitations")
	private List <Event> eventInvitations = new  ArrayList <Event>() ;
	
	@JsonIgnore
	@OneToMany (mappedBy = "profile")
	private List <Suggestion> suggestions = new ArrayList<Suggestion> () ;
	
	
	public int getUser_Id() {
		return user_Id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }


    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    @JsonIgnore
	public List <Profile> getFriends() {
		return Friends;
	}

    @JsonIgnore
	public void setFriends(List <Profile> friends) {
		Friends = friends;
	}
    @JsonIgnore
	public List<Event> getEvents() {
		return events;
	}
	@JsonIgnore
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	@JsonIgnore
	public List<Event> getEventsWhoOwn() {
		return eventsWhoOwn;
	}
	@JsonIgnore
	public void setEventsWhoOwn(List<Event> eventsWhoOwn) {
		this.eventsWhoOwn = eventsWhoOwn;
	}

	@JsonIgnore
	public List<Area> getAreasWhoOwn() {
		return areasWhoOwn;
	}

	@JsonIgnore
	public void setAreasWhoOwn(List<Area> areasWhoOwn) {
		this.areasWhoOwn = areasWhoOwn;
	}

	@JsonIgnore
	public List<area_profile> getAreas() {
		return areas;
	}

	@JsonIgnore
	public void setAreas(List<area_profile> areas) {
		this.areas = areas;
	}

	@JsonIgnore
	public History getHistory() {
		return history;
	}

	@JsonIgnore
	public void setHistory(History history) {
		this.history = history;
	}

	@JsonIgnore
	public List<Profile> getRequestedto() {
		return Requestedto;
	}

	@JsonIgnore
	public void setRequestedto(List<Profile> requestedto) {
		Requestedto = requestedto;
	}

	@JsonIgnore
	public List<Profile> getPendingRequests() {
		return pendingRequests;
	}

	@JsonIgnore
	public void setPendingRequests(List<Profile> pendingRequests) {
		this.pendingRequests = pendingRequests;
	}

	@JsonIgnore
	public List<Event> getEventInvitations() {
		return eventInvitations;
	}

	@JsonIgnore
	public void setEventInvitations(List<Event> eventInvitations) {
		this.eventInvitations = eventInvitations;
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
