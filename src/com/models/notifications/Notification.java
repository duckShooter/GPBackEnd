package com.models.notifications;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.models.user.Profile;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) //Table per concrete class
public abstract class Notification {
	
	@Id
	@GeneratedValue
	protected int notification_id;
	
	@Temporal(TemporalType.TIMESTAMP)
	protected Date timestamp;
	 
	@Column(nullable=false)
	protected boolean marked;

	@ManyToOne(optional = false) //Mandatory relationship
	@JoinColumn(name = "owner_id")
	protected Profile owner;
	
	public Notification() {
		//Empty
	}
	
	public Notification(Date timestamp, Profile owner) {
		this.timestamp = timestamp;
		this.owner = owner;
	}
	
	public int getNotification_id() {
		return notification_id;
	}

	public void setNotification_id(int notification_id) {
		this.notification_id = notification_id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}
	
	
	public Profile getOwner() {
		return owner;
	}

	public void setOwner(Profile owner) {
		this.owner = owner;
	}
	

	public String toJsonString() {
		return null;
	}
}
