package com.models.notifications;

import com.models.event.Event;
import com.models.user.Profile;

public class EventArrivalNotification extends Notification {
	private Event event;
	private Profile arrivedUser;
}
