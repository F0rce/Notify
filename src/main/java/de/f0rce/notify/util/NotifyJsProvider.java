package de.f0rce.notify.util;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;

/**
 * Including JS resources.
 * 
 * @author David "F0rce" Dodlek
 */
@JsModule("./@f0rce/notify/notify.js")
@Tag("div")
public class NotifyJsProvider extends Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5696482326311163330L;

	public NotifyJsProvider() {
		this.setId("notify-v14");
	}

}
