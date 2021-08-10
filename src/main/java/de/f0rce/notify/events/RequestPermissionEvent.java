package de.f0rce.notify.events;

import com.vaadin.flow.component.ComponentEvent;

import de.f0rce.notify.util.NotifyJsProvider;

public class RequestPermissionEvent extends ComponentEvent<NotifyJsProvider> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3116933775226230769L;
	private boolean permission;

	public RequestPermissionEvent(NotifyJsProvider source, boolean fromClient, boolean permission) {
		super(source, fromClient);

		this.permission = permission;
	}

	/**
	 * Returns if the user accepts notifications or not.
	 * 
	 * @return boolean
	 */
	public boolean hasPermission() {
		return this.permission;
	}

}
