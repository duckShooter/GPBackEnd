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

import org.json.simple.JSONObject;

import com.models.user.Profile;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) //Table per concrete class
public abstract class Notification {
	
	@Id
	@GeneratedValue
	protected int id;
	
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
	
	@SuppressWarnings("deprecation")
	public Notification(Profile owner) {
		timestamp = new Date();
		if (this.timestamp.getHours()+6 > 24)
			this.timestamp.setHours((timestamp.getHours()+6)-24);
		else 
			timestamp.setHours(timestamp.getHours()+6);
		this.owner = owner;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public JSONObject toJsonObject() {
		return null;
	}
}
