package com.models.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.boon.json.annotations.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.models.event.Event;

@Entity
public class Profile  {
	@JsonIgnore
	@Id @GeneratedValue(generator = "newGenerator") //name of the primary key generator
	@GenericGenerator(name = "newGenerator", strategy = "foreign",parameters = {@Parameter(value = "account", name = "property") })
	private int User_Id ;
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "User_Id")
	private Account account = new Account() ;
	
	@JsonIgnore
	@OneToMany (cascade = CascadeType.ALL , mappedBy = "owner")
	private List <Event> eventsWhoOwn = new ArrayList <Event> () ;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL , mappedBy = "users")
	private List <Event> events = new ArrayList <Event> () ;	
	
	private String firstName;
    private String lastName;
    private String email;
    private String homeTown;
    private String name;
   
    //@Temporal (TemporalType.DATE)
    private String birthday;
    
	private String pictureURL;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private Set <Profile> Friends = new HashSet <Profile> () ;

    

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
	public Set<Profile> getFriends() {
		return Friends;
	}

    @JsonIgnore
	public void setFriends(Set<Profile> friends) {
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

	
	
    
    
}
