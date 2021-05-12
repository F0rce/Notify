package de.f0rce.notify.events;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;

import de.f0rce.notify.Notify;

@DomEvent("requested-permission")
public class RequestPermissionEvent extends ComponentEvent<Notify> {

	private String permission;

	public RequestPermissionEvent(Notify source, boolean fromClient,
			@EventData("event.detail.permission") String permission) {
		super(source, fromClient);

		this.permission = permission;
	}

	public String getPermission() {
		return this.permission;
	}

}
