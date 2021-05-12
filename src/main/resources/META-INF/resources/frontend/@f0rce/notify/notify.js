import { LitElement } from "lit-element";

class NotifyV14 extends LitElement {
  static get properties() {
    return {
      supported: { type: Boolean },
    };
  }

  constructor() {
    super();
    this.supported = true;
  }

  async connectedCallback() {
    super.connectedCallback();

    // Initial check if Notification is supported
    if (!("Notification" in window)) {
      this.supported = false;
    }
  }

  _requestPermission() {
    if (!this.supported) return;
    let self = this;
    Notification.requestPermission().then((res) => {
      self.dispatchEvent(
        new CustomEvent("requested-permission", {
          detail: {
            permission: res,
          },
        })
      );
    });
  }

  _sendNotification(id, title, body, icon) {
    if (!this.supported) return;
    var notification = new Notification(title, {
      body: body,
      icon: icon,
    });
    var self = this;
    notification.onclick = function (event) {
      event.preventDefault();
      self.dispatchEvent(
        new CustomEvent("notification-click", {
          detail: {
            id: id,
          },
        })
      );
    };
    notification.onerror = async function (event) {
      event.preventDefault();
      let permission = await Notification.requestPermission();
      let errorMsg = "Not Specified."
      if (permission !== "granted") {
          errorMsg = "Insufficient Permissions.";
      }
      self.dispatchEvent(
        new CustomEvent("notification-error", {
          detail: {
            id: id,
            error: errorMsg,
          },
        })
      );
    };
    notification.onshow = function (event) {
      event.preventDefault();
      self.dispatchEvent(
        new CustomEvent("notification-show", {
          detail: {
            id: id,
          },
        })
      );
    };
    notification.onclose = function (event) {
      event.preventDefault();
      self.dispatchEvent(
        new CustomEvent("notification-close", {
          detail: {
            id: id,
          },
        })
      );
    };
  }
}

customElements.define("notify-v14", NotifyV14);
