package de.f0rce.notify.util;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.Command;

public final class NotifyUtil {

	private NotifyUtil() {

	}

	/**
	 * Executes a given {@link Command} after ensuring that the {@link UI} is
	 * accessible or already accessed.
	 *
	 * @author Gerrit Sedlaczek
	 *
	 * @param ui      {@link UI}
	 * @param command the comamnd to execute in the ui thread
	 */
	public static void securelyAccessUI(UI ui, Command command) {
		if (ui != null && !ui.isClosing() && ui.getSession() != null) {
			if (ui.getSession().hasLock()) {
				command.execute();
			} else {
				ui.access(command);
			}
		}
	}

}
