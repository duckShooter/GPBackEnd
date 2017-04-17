package com.model.notifications;

/*
 * [Useful Links]
 * 	- More information about notification content: 
 * 		https://firebase.google.com/docs/notifications/android/console-audience
 * 	- More about the device registration token:
 * 		https://firebase.google.com/docs/notifications/android/console-device
 */
public interface Notification {
	public String toJsonString();
}
