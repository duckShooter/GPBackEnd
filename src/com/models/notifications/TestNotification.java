package com.models.notifications;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.persistence.Transient;

import org.json.simple.JSONObject;

@Deprecated
public class TestNotification extends Notification {
	
	@Transient //Don't save in database - needed only for firebase request body
	private String title;
	
	private String body;
	
	@Transient //Don't save in database - needed only for firebase request body
	private HashMap<String, String> data = new HashMap<>();
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public void addDataPair(String a, String b) {
		data.put(a, b);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String toJsonString() {
		JSONObject dataPairs = new JSONObject();
		for(Entry<String, String> e : data.entrySet()) {
			dataPairs.put(e.getKey(), e.getValue());
		}
		
		JSONObject notificationPairs = new JSONObject();
		notificationPairs.put("title", title);
		notificationPairs.put("body", body);
		
		JSONObject json = new JSONObject();
		json.put("notification", notificationPairs);
		json.put("data", dataPairs);
		json.put("priority", "high");
		json.put("to", "cL-2zBBTWbA:APA91bHvP72TGqdAcurFQPyL75ZAkZs-Qk7I--ilHL5lMUIFCqJ8bI6wuYlxwdgzlAUfssImbLNrbPDp5a1Xgl1HOPM6Gi-CkoLFLR2tpKYvgMq6Yv3c-dmDKVMsk_98acM7bbFbMwqu");
		return json.toJSONString();
	}
	
	//Local Testing
	public static void main(String[] args) {
		/*TestNotification s = new TestNotification();
		s.body = "sdfsf";
		s.title = "sadasd";
		s.data = new HashMap<>();
		s.data.put("A", "sdfsdf");
		s.data.put("ew", "sdfsdf");
		System.out.println(s.toJsonString());*/
		//==============================================================
		/*double lat1 = 24.504980; double lon1 = -102.341280 ;
		double lat2 = 24.504988; double lon2 = -102.340064;
		final double earthRadius = 6371.0 * 1000; //meter
		final double radius = 200.0;
		double deltaLat = Math.toRadians(lat1 - lat2); //Degrees to radians
		double deltaLon = Math.toRadians(lon1 - lon2);
		double tempA = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) + Math.cos(Math.toRadians(lat1)) 
						* Math.cos(Math.toRadians(lat2)) * Math.sin(deltaLon/2) * Math.sin(deltaLon/2);
		double tempC = 2 * Math.atan2(Math.sqrt(tempA), Math.sqrt(1.0 - tempA));
		double distance = earthRadius * tempC;						
		System.out.println("distance: " + distance + " | radius: " + radius + " | the point is " + (distance <= radius ? "inside" : "outside") + " the area."); */
	}
	
}
