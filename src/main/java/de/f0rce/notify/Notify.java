package de.f0rce.notify;

import java.io.Serializable;
import java.util.Objects;

import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;

import de.f0rce.notify.events.RequestPermissionEvent;
import de.f0rce.notify.util.Notification;
import de.f0rce.notify.util.NotifyJsProvider;
import de.f0rce.notify.util.NotifyUtil;

public class Notify implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5577403258951246980L;

	/**
	 * All needed JavaScript Methods that need to be sent to the frontend.
	 * 
	 * @author david.dodlek
	 */
	public interface JS_METHODS {
		String REQUEST_PERMISSION = "return window.notify.requestPermission()";
		String GET_PERMISSION = "return Notification.permission";
		String SEND_NOTIFICATION_FULL = "window.notify.sendNotification($0, $1, $2, $3)";
		String SEND_NOTIFICATION_NO_ICON = "window.notify.sendNotification($0, $1, $2)";
		String SEND_NOTIFICATION_FULL_DELAY = "window.notify.sendNotification($0, $1, $2, $3, $4)";
	}

	private final UI defaultUI;
	private final NotifyJsProvider jsProvider = new NotifyJsProvider();

	private boolean hasPermission = false;

	public Notify(UI notifyUI) {
		this.defaultUI = notifyUI;

		notifyUI.add(jsProvider);

		this.updatePermission();
	}

	/**
	 * Request the permission to send notifications to the user.
	 */
	public void requestPermission() {
		NotifyUtil.securelyAccessUI(defaultUI,
				() -> defaultUI.getPage().executeJs(JS_METHODS.REQUEST_PERMISSION).then(String.class, permission -> {
					if (permission == "granted") {
						this.hasPermission = true;
					} else {
						this.hasPermission = false;
					}
					ComponentUtil.fireEvent(jsProvider, new RequestPermissionEvent(jsProvider, false, hasPermission));
				}));
	}

	/**
	 * Get the current permission. (Returned value may not be synced!)
	 * 
	 * @return
	 */
	public boolean getPermission() {
		updatePermission();
		return this.hasPermission;

	}

	public void runAfterGetPermission(Runnable action) {
		Objects.requireNonNull(action);
		updatePermission(action);
	}

	private void updatePermission() {
		NotifyUtil.securelyAccessUI(defaultUI, () -> {
			defaultUI.getPage().executeJs(JS_METHODS.GET_PERMISSION).then(String.class, permission -> {
				if (permission.equals("granted")) {
					this.hasPermission = true;
				} else {
					this.hasPermission = false;
				}
			});
		});
	}

	private void updatePermission(Runnable action) {
		NotifyUtil.securelyAccessUI(defaultUI, () -> {
			defaultUI.getPage().executeJs(JS_METHODS.GET_PERMISSION).then(String.class, permission -> {
				if (permission.equals("granted")) {
					this.hasPermission = true;
					action.run();
				} else {
					this.hasPermission = false;
					action.run();
				}
			});
		});
	}

	/**
	 * Send a notification to the user. Make sure you requested the permission
	 * already {@link #requestPermission()}. Only the
	 * {@link Notification#onClick(com.vaadin.flow.component.ComponentEventListener)}
	 * and the
	 * {@link Notification#onClose(com.vaadin.flow.component.ComponentEventListener)}
	 * events will work due to the other events being sent before the listeners can
	 * be added. If you want to listen to those events aswell, be sure to use
	 * {@link #createNotification(String, String)} or
	 * {@link #createNotification(String, String, String)} --> add the listeners you
	 * need and use {@link Notification#send()} / {@link Notification#send(int)}
	 * afterwards.
	 * 
	 * @param title the title
	 * @param body  the description
	 * @param icon  an url to the icon (can also be an uri or filepath)
	 * @return {@link Notification}
	 */
	public Notification sendNotification(String title, String body, String icon) {
		Notification notification = new Notification(defaultUI, title, body, icon);
		NotifyUtil.securelyAccessUI(defaultUI, () -> {
			defaultUI.add(notification);
			defaultUI.getPage().executeJs(JS_METHODS.SEND_NOTIFICATION_FULL, notification.getIdentifier(),
					notification.getTitle(), notification.getBody(), notification.getIcon());
		});
		return notification;
	}

	/**
	 * Send a notification to the user. Make sure you requested the permission
	 * already {@link #requestPermission()}. Only the
	 * {@link Notification#onClick(com.vaadin.flow.component.ComponentEventListener)}
	 * and the
	 * {@link Notification#onClose(com.vaadin.flow.component.ComponentEventListener)}
	 * events will work due to the other events being sent before the listeners can
	 * be added. If you want to listen to those events aswell, be sure to use
	 * {@link #createNotification(String, String)} or
	 * {@link #createNotification(String, String, String)} --> add the listeners you
	 * need and use {@link Notification#send()} / {@link Notification#send(int)}
	 * afterwards.
	 * 
	 * @param title the title
	 * @param body  the description
	 * @return {@link Notification}
	 */
	public Notification sendNotification(String title, String body) {
		Notification notification = new Notification(defaultUI, title, body);
		NotifyUtil.securelyAccessUI(defaultUI, () -> {
			defaultUI.add(notification);
			defaultUI.getPage().executeJs(JS_METHODS.SEND_NOTIFICATION_NO_ICON, notification.getIdentifier(),
					notification.getTitle(), notification.getBody());
		});
		return notification;
	}

	/**
	 * Creates a notification but doesn't send it right away. Make sure to use
	 * {@link Notification#send()} or {@link Notification#send(int)} to send it to
	 * the user.
	 * 
	 * @param title
	 * @param body
	 * @param icon
	 * @return {@link Notification}
	 */
	public Notification createNotification(String title, String body, String icon) {
		return new Notification(defaultUI, title, body, icon);
	}

	/**
	 * Creates a notification but doesn't send it right away. Make sure to use
	 * {@link Notification#send()} or {@link Notification#send(int)} to send it to
	 * the user.
	 * 
	 * @param title
	 * @param body
	 * @return {@link Notification}
	 */
	public Notification createNotification(String title, String body) {
		return new Notification(defaultUI, title, body);
	}
}
