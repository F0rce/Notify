package de.f0rce.notify.events;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;

import de.f0rce.notify.util.Notification;

@DomEvent("notification-error")
public class NotificationErrorEvent extends ComponentEvent<Notification> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2020242169285788818L;
	private String id;
	private String error;

	public NotificationErrorEvent(Notification source, boolean fromClient, @EventData("event.detail.id") String id,
			@EventData("event.detail.error") String error) {
		super(source, fromClient);

		this.id = id;
		this.error = error;
	}

	/**
	 * Returns the {@link Notification}'s unique identifier.
	 * 
	 * @return {@link String}
	 */
	public String getIdentifier() {
		return this.id;
	}

	/**
	 * Returns the error message sent by the {@link Notification}.
	 * 
	 * @return {@link String}
	 */
	public String getError() {
		return this.error;
	}

}
