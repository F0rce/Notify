package de.f0rce.notify.events;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;

import de.f0rce.notify.util.Notification;

@DomEvent("notification-show")
public class NotificationShowEvent extends ComponentEvent<Notification> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2770374132379018012L;
	private String id;

	public NotificationShowEvent(Notification source, boolean fromClient, @EventData("event.detail.id") String id) {
		super(source, fromClient);

		this.id = id;
	}

	/**
	 * Returns the {@link Notification}'s unique identifier.
	 * 
	 * @return {@link String}
	 */
	public String getIdentifier() {
		return this.id;
	}

}
