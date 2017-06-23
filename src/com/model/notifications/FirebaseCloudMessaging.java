package com.model.notifications;

import java.io.IOException;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

/*
 * [Useful Links]
 * 	- More about firebase authentication
 * 		https://firebase.google.com/docs/cloud-messaging/server
 */

@Deprecated
public class FirebaseCloudMessaging {
	private final String connectionURL = "https://fcm.googleapis.com/fcm/send";
	private final String apiKey = "AAAA6M8l4a8:APA91bEPzrrw1FSk6dD1LFu3bo6fnCAycSHuzKSs61pQt6Bmy6YWHApP-GiWe3Uh8oeuhuuoJJp6ryCDbdQAlWuwguzk8AdEEX7g0q2O4xARU_cYoIVEVmierOXWsNBhFDjOdMtaRnCJ";
	private HttpClient client;
	
	public FirebaseCloudMessaging() {
			client = HttpClientBuilder.create().build();
	}
	
	public void sendNotification(Notification notification) throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost(connectionURL);
		request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
		request.setHeader(HttpHeaders.AUTHORIZATION, "key="+ apiKey);
		request.setEntity(new StringEntity(notification.toJsonString()));
		System.out.println(new BasicResponseHandler().handleResponse(client.execute(request)));		
	}
	
	//Local Testing
	public static void main(String[] args) {
		FirebaseCloudMessaging fcm = new FirebaseCloudMessaging();
		try {
			TestNotification x = new TestNotification();
			x.setBody("fuck");
			x.setTitle("fuckThePolice");
			x.addDataPair("hello", "world");
			x.addDataPair("penis", "long");
			fcm.sendNotification(new TestNotification());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
