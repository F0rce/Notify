package de.f0rce.notify.util;

import java.util.UUID;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.shared.Registration;

import de.f0rce.notify.Notify;
import de.f0rce.notify.events.NotificationClickEvent;
import de.f0rce.notify.events.NotificationCloseEvent;
import de.f0rce.notify.events.NotificationErrorEvent;
import de.f0rce.notify.events.NotificationShowEvent;

@Tag("pre")
public class Notification extends Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2351775376104017373L;
	private UI currentUI;
	private String identifier;
	private String title;
	private String body;
	private String icon = "";

	/**
	 * Create a new Notification. Be sure to use
	 * {@link Notify#createNotification(String, String, String)} in order to use the
	 * same {@link UI}.
	 * 
	 * @param ui    where the notification will be displayed - has to match the
	 *              {@link UI} in {@link Notify}'s default constructor.
	 * @param title the title of the notification
	 * @param body  the body of the notification
	 * @param icon  the icon of the notification (uri, filepath or url)
	 */
	public Notification(UI ui, String title, String body, String icon) {
		this.currentUI = ui;
		this.identifier = UUID.randomUUID().toString();
		this.setId(this.identifier);
		this.title = title;
		this.body = body;
		this.icon = icon;
	}

	/**
	 * Create a new Notification. Be sure to use
	 * {@link Notify#createNotification(String, String)} in order to use the same
	 * {@link UI}.
	 * 
	 * @param ui    where the notification will be displayed - has to match the
	 *              {@link UI} in {@link Notify}'s default constructor.
	 * @param title the title of the notification
	 * @param body  the body of the notification
	 */
	public Notification(UI ui, String title, String body) {
		this.currentUI = ui;
		this.identifier = UUID.randomUUID().toString();
		this.setId(this.identifier);
		this.title = title;
		this.body = body;
	}

	/**
	 * Returns the random generated UUID (Identifier) for the frontend to
	 * differenciate between the different events.
	 * 
	 * @return {@link String}
	 */
	public String getIdentifier() {
		return this.identifier;
	}

	/**
	 * Returns the current title.
	 * 
	 * @return {@link String}
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Returns the current body.
	 * 
	 * @return {@link String}
	 */
	public String getBody() {
		return this.body;
	}

	/**
	 * Returns the current icon (uri, filepath or url).
	 * 
	 * @return {@link String}
	 */
	public String getIcon() {
		return this.icon;
	}

	/**
	 * Send the notification to the user.
	 */
	public void send() {
		NotifyUtil.securelyAccessUI(currentUI, () -> {
			currentUI.add(this);
			currentUI.getPage().executeJs(Notify.JS_METHODS.SEND_NOTIFICATION_FULL, this.identifier, this.title,
					this.body, this.icon);
		});
	}

	/**
	 * Send the notification to the user.
	 * 
	 * @param ms the time the notification will be displayed in milliseconds
	 */
	public void send(int ms) {
		NotifyUtil.securelyAccessUI(currentUI, () -> {
			currentUI.add(this);
			currentUI.getPage().executeJs(Notify.JS_METHODS.SEND_NOTIFICATION_FULL_DELAY, this.identifier, this.title,
					this.body, this.icon, ms);
		});
	}

	/**
	 * Adds a listener which listens to the "onclick" event sent by the
	 * notifications.
	 * 
	 * @param listener {@link NotificationClickEvent}
	 * @return {@link Registration}
	 */
	public Registration onClick(ComponentEventListener<NotificationClickEvent> listener) {
		return addListener(NotificationClickEvent.class, listener);
	}

	/**
	 * Adds a listener which listens to the "onerror" event sent by the
	 * notifications.
	 * 
	 * @param listener {@link NotificationErrorEvent}
	 * @return {@link Registration}
	 */
	public Registration onError(ComponentEventListener<NotificationErrorEvent> listener) {
		return addListener(NotificationErrorEvent.class, listener);
	}

	/**
	 * Adds a listener which listens to the "onshow" event sent by the
	 * notifications.
	 * 
	 * @param listener {@link NotificationShowEvent}
	 * @return {@link Registration}
	 */
	public Registration onShow(ComponentEventListener<NotificationShowEvent> listener) {
		return addListener(NotificationShowEvent.class, listener);
	}

	/**
	 * Adds a listener which listens to the "onclose" event sent by the
	 * notifications.
	 * 
	 * @param listener {@link NotificationCloseEvent}
	 * @return {@link Registration}
	 */
	public Registration onClose(ComponentEventListener<NotificationCloseEvent> listener) {
		return addListener(NotificationCloseEvent.class, listener);
	}

}
