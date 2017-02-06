package com.models.user;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class Profile implements Serializable {
	
	
	@Id @GeneratedValue(generator = "newGenerator") //name of the primary key generator
	@GenericGenerator(name = "newGenerator", strategy = "foreign",parameters = { @Parameter(value = "account", name = "property") })
	private int User_Id ;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "User_Id")
	private Account account = new Account() ;
    
	private String firstName;
    private String lastName;
    private String email;
    private String homeTown;
    private String name;
   
    //@Temporal (TemporalType.DATE)
    private String birthday;
    
	private String pictureURL;
	
	@ManyToMany(cascade = CascadeType.ALL)
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

	public Set<Profile> getFriends() {
		return Friends;
	}

	public void setFriends(Set<Profile> friends) {
		Friends = friends;
	}
    
    
}
