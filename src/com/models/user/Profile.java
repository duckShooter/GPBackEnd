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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.boon.json.annotations.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.models.event.Event;
import com.models.location.Area;

@Entity
public class Profile  {
	
	@Id @GeneratedValue(generator = "newGenerator") //name of the primary key generator
	@GenericGenerator(name = "newGenerator", strategy = "foreign",parameters = {@Parameter(value = "account", name = "property") })
	private int User_Id ;
	
	
	private String firstName;
    private String lastName;
    private String email;
    private String homeTown;
    private String name;
   
    //@Temporal (TemporalType.DATE)
    private String birthday;
    
	private String pictureURL;
	
	
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
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private List <Profile> Friends = new ArrayList <Profile> () ;

	@JsonIgnore
	@OneToMany (cascade = CascadeType.ALL,mappedBy = "owner")
    private List <Area> areasWhoOwn = new ArrayList<Area> () ;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL , mappedBy = "users")
	private List <Area> areas = new ArrayList<Area> () ;

	@JsonIgnore
	@OneToOne (cascade = CascadeType.ALL)
	@JoinColumn(name = "history_id")
	private History history = new History() ; 
	
	public int getUser_Id() {
		return User_Id;
	}

	public void setUser_Id(int user_Id) {
		User_Id = user_Id;
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
	public List<Area> getAreas() {
		return areas;
	}

	@JsonIgnore
	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public History getHistory() {
		return history;
	}

	public void setHistory(History history) {
		this.history = history;
	} 
	
	
	
}
