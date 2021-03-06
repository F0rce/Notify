package de.f0rce.notify;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

import de.f0rce.notify.util.Notification;

@Route("")
public class View extends Div {

	public View() {
		Notify notify = new Notify(UI.getCurrent());
		Button button = new Button("Request Permission");
		Button button2 = new Button("Get Permission");
		Button button3 = new Button("Send Notification (Icon)");
		Button button4 = new Button("Send Notification");

		button.addClickListener(event -> {
			notify.requestPermission();
		});
		button2.addClickListener(event -> {
			notify.runAfterGetPermission(new Runnable() {

				@Override
				public void run() {
					System.out.println(notify.getPermission());
				}
			});
		});
		button3.addClickListener(event -> {
			Notification noti = notify.createNotification("test", "test",
					"data:image/jpeg;base64,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");
			noti.onClick(evt -> {
				System.out.println("CLICK " + evt.getIdentifier());
			});
			noti.onError(evt -> {
				System.out.println("ERROR " + evt.getError());
			});
			noti.onClose(evt -> {
				System.out.println("CLOSE " + evt.getIdentifier());
			});
			noti.onShow(evt -> {
				System.out.println("SHOW " + evt.getIdentifier());
			});
			noti.send();
		});
		button4.addClickListener(event -> {
			Notification noti = notify.createNotification("Testing", "Diese Benachrichtigung bleibt 3 sekunden");
			noti.onShow(evt -> {
				System.out.println("SHOW " + evt.getIdentifier());
			});
			noti.onClose(evt -> {
				System.out.println("CLOSE " + evt.getIdentifier());
			});
			noti.send(3000);
		});

		add(button, button2, button3, button4);
	}
}
