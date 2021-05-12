package de.f0rce.notify.events;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;

import de.f0rce.notify.Notify;

@DomEvent("notification-error")
public class NotificationErrorEvent extends ComponentEvent<Notify> {

	private String id;
	private String error;

	public NotificationErrorEvent(Notify source, boolean fromClient, @EventData("event.detail.id") String id,
			@EventData("event.detail.error") String error) {
		super(source, fromClient);

		this.id = id;
		this.error = error;
	}

	public String getId() {
		return this.id;
	}

	public String getError() {
		return this.error;
	}

}
