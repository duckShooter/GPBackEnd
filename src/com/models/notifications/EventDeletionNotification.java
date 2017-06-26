package com.models.notifications;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.models.event.Event;

@Entity
public class EventDeletionNotification extends Notification {
	@ManyToOne (optional = false)
	@JoinColumn(name = "event_id")
	private Event event;
}
