package com.models.notifications;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.models.location.Area;
import com.models.location.AreaProfile;
import com.models.notifications.Notification;
import com.models.user.Profile;

@Entity
public class AreaLeftNotification extends Notification {
	@ManyToOne(optional = false, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "area_id")
	private Area area;
	
	@ManyToOne(optional = false, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "target_id")
	private Profile target;
	
	public AreaLeftNotification() {
		//Empty
	}
	
	public AreaLeftNotification(Profile owner, Area area, Profile target) {
		super(owner);
		this.area = area;
		this.target = target;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Profile getTarget() {
		return target;
	}

	public void setTarget(Profile target) {
		this.target = target;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJsonObject() {
		JSONObject jsonString = new JSONObject();
		
		JSONObject areaLocation = new JSONObject();
		areaLocation.put("location_id", area.getLocation().getLocation_id());
		areaLocation.put("name", area.getLocation().getName());
		areaLocation.put("latitude", area.getLocation().getLatitude());
		areaLocation.put("longitude", area.getLocation().getLongitude());

		JSONArray areaUsers = new JSONArray();
		JSONObject tempUser;
		for(AreaProfile p : area.getUsers()) {
			tempUser = new JSONObject();
			tempUser.put("id", p.getProfile().getUser_Id());
			tempUser.put("firstName", p.getProfile().getFirstName());
			tempUser.put("lastName", p.getProfile().getLastName());
			tempUser.put("email", p.getProfile().getEmail());
			tempUser.put("homeTown", p.getProfile().getHomeTown());
			tempUser.put("birthday", p.getProfile().getBirthday());
			tempUser.put("pictureURL", p.getProfile().getPictureURL());
			areaUsers.add(tempUser);
		}
		
		JSONObject target = new JSONObject();
		target.put("area_id", area.getArea_id());
		target.put("radius", area.getRedius());
		target.put("area_id", area.getArea_id());
		target.put("location", areaLocation);
		target.put("users", areaUsers);
		
		jsonString.put("id", id);
		jsonString.put("owner_id", owner.getUser_Id());
		jsonString.put("target", target);
		jsonString.put("type", NotificationType.AREA_LEFT.toString());
		jsonString.put("timestamp", timestamp.getTime());
		jsonString.put("read", marked);
		return jsonString;
	}
}
