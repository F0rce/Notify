package de.f0rce.notify.events;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;

import de.f0rce.notify.Notify;

@DomEvent("notification-close")
public class NotificationCloseEvent extends ComponentEvent<Notify> {

	private String id;

	public NotificationCloseEvent(Notify source, boolean fromClient, @EventData("event.detail.id") String id) {
		super(source, fromClient);

		this.id = id;
	}

	public String getId() {
		return this.id;
	}

}
