window.notify = {
  isSupported: true,

  requestPermission: async function () {
    if (!("Notification" in window)) {
      this.isSupported = false;
    }
    let per = await Notification.requestPermission();
    return per;
  },

  sendNotification: function (id, title, body, icon, delay) {
    if (!this.isSupported) return;

    const notifyDiv = document.getElementById(id);

    const notification = new Notification(title, {
      body: body,
      icon: icon,
    });

    notification.onclick = function (event) {
      event.preventDefault();
      notifyDiv.dispatchEvent(
        new CustomEvent("notification-click", {
          detail: {
            id: id,
          },
        })
      );
      notifyDiv.remove();
    };
    notification.onerror = async function (event) {
      event.preventDefault();
      let permission = Notification.permission;
      let errorMsg = "Not Specified.";
      if (permission !== "granted") {
        errorMsg = "Insufficient Permissions.";
      }
      notifyDiv.dispatchEvent(
        new CustomEvent("notification-error", {
          detail: {
            id: id,
            error: errorMsg,
          },
        })
      );
      notifyDiv.remove();
    };
    notification.onshow = function (event) {
      event.preventDefault();
      notifyDiv.dispatchEvent(
        new CustomEvent("notification-show", {
          detail: {
            id: id,
          },
        })
      );
    };
    notification.onclose = function (event) {
      event.preventDefault();
      notifyDiv.dispatchEvent(
        new CustomEvent("notification-close", {
          detail: {
            id: id,
          },
        })
      );
      notifyDiv.remove();
    };

    if (delay !== undefined) {
      setTimeout(function () {
        notification.close();
      }, delay);
    }
  },
};
