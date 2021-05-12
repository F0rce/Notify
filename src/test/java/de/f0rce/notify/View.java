package de.f0rce.notify;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("")
public class View extends Div {

	public View() {
		Notify notify = new Notify();
		Button button = new Button("Request Permission");
		button.addClickListener(event -> {
			notify.requestPermission(new Runnable() {

				@Override
				public void run() {

					notify.sendNotification("Download abgeschlossen.",
							"Klicken Sie auf diese Benachrichtigung um sich die heruntergeladenen Daten anzuzeigen!\n\nDankeschön :)",
							"");
					notify.addNotificationClickListener(event -> {
						System.out.println("EVENT ANGEKLICKT");
					});

					notify.addNotificationShowListener(event -> {
						System.out.println("EVENT GEZEIGT");
					});

					notify.addNotificationCloseListener(event -> {
						System.out.println("EVENT GESCHLOSSEN");
					});

					notify.addNotificationErrorListener(event -> {
						System.out.println("EVENT ERROR");
					});

				}
			});
		});
		add(notify, button);
	}
}
