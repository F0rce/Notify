package de.f0rce.notify;

import java.util.Objects;
import java.util.UUID;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.shared.Registration;

import de.f0rce.notify.events.NotificationClickEvent;
import de.f0rce.notify.events.NotificationCloseEvent;
import de.f0rce.notify.events.NotificationErrorEvent;
import de.f0rce.notify.events.NotificationShowEvent;
import de.f0rce.notify.events.RequestPermissionEvent;

@Tag("notify-v14")
@JsModule("./@f0rce/notify/notify.js")
public class Notify extends Component {

	public static String PERMISSION_GRANTED = "granted";
	public static String PERMISSION_DENIED = "denied";
	public static String PERMISSION_DEFAULT = "default";

	private String permission = null;

	public Notify() {
	}

	/**
	 * Request the permission to send notifications to the user.
	 * 
	 * @param action an action to be run after the promise in the frontend has been
	 *               resolved
	 */
	public void requestPermission(Runnable action) {
		Objects.requireNonNull(action);
		addListener(RequestPermissionEvent.class, event -> runAfterPermsissionRequest(event, action));
		getElement().callJsFunction("_requestPermission");
	}

	private void runAfterPermsissionRequest(RequestPermissionEvent event, Runnable action) {
		this.permission = event.getPermission();
		event.unregisterListener();
		action.run();
	}

	/**
	 * Request the permission to send notifications to the user. Everything is
	 * happening in the background so the current permission
	 * {@link #getPermission()} <b>could</b> not reflect the current permission.
	 */
	public void requestPermission() {
		addListener(RequestPermissionEvent.class, event -> {
			this.permission = event.getPermission();
			event.unregisterListener();
		});
		getElement().callJsFunction("_requestPermission");
	}

	/**
	 * Get the current permission.
	 * 
	 * @return returned {@link String} can be granted, denied or default (which is
	 *         interpreted by the browser as denied aswell).
	 */
	public String getPermission() {
		return this.permission;
	}

	/**
	 * Send a notification to the user.
	 * 
	 * @param title the title
	 * @param body  the description
	 * @param icon  an url to the icon
	 * @return a random generated {@link String} id to differentiate the
	 *         notifications
	 */
	public String sendNotification(String title, String body, String icon) {
		String _id = UUID.randomUUID().toString();
		getElement().callJsFunction("_sendNotification", _id, title, body, icon);
		return _id;
	}

	/**
	 * Send a notification to the user.
	 * 
	 * @param id    a {@link String} to give the notification a "name"
	 * @param title the title
	 * @param body  the description
	 * @param icon  an url to the icon (can also be an uri or filepath)
	 * @return the set id
	 */
	public String sendNotification(String id, String title, String body, String icon) {
		getElement().callJsFunction("_sendNotification", id, title, body, icon);
		return id;
	}

	/**
	 * Adds a listener which listens to the "onclick" event sent by the
	 * notifications.
	 * 
	 * @param listener {@link NotificationClickEvent}
	 * @return {@link Registration}
	 */
	public Registration addNotificationClickListener(ComponentEventListener<NotificationClickEvent> listener) {
		return addListener(NotificationClickEvent.class, listener);
	}

	/**
	 * Adds a listener which listens to the "onerror" event sent by the
	 * notifications.
	 * 
	 * @param listener {@link NotificationErrorEvent}
	 * @return {@link Registration}
	 */
	public Registration addNotificationErrorListener(ComponentEventListener<NotificationErrorEvent> listener) {
		return addListener(NotificationErrorEvent.class, listener);
	}

	/**
	 * Adds a listener which listens to the "onshow" event sent by the
	 * notifications.
	 * 
	 * @param listener {@link NotificationShowEvent}
	 * @return {@link Registration}
	 */
	public Registration addNotificationShowListener(ComponentEventListener<NotificationShowEvent> listener) {
		return addListener(NotificationShowEvent.class, listener);
	}

	/**
	 * Adds a listener which listens to the "onclose" event sent by the
	 * notifications.
	 * 
	 * @param listener {@link NotificationCloseEvent}
	 * @return {@link Registration}
	 */
	public Registration addNotificationCloseListener(ComponentEventListener<NotificationCloseEvent> listener) {
		return addListener(NotificationCloseEvent.class, listener);
	}
}
