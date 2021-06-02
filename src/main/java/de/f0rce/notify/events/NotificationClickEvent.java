package de.f0rce.notify.events;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;

import de.f0rce.notify.util.Notification;

@DomEvent("notification-click")
public class NotificationClickEvent extends ComponentEvent<Notification> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5569445337047430019L;
	private String identifier;

	public NotificationClickEvent(Notification source, boolean fromClient, @EventData("event.detail.id") String id) {
		super(source, fromClient);

		this.identifier = id;
	}

	/**
	 * Returns the {@link Notification}'s unique identifier.
	 * 
	 * @return {@link String}
	 */
	public String getIdentifier() {
		return this.identifier;
	}

}
