import { html } from "@polymer/lit-element";
import { PageViewElement } from "../page-view-element.js";

// These are the shared styles needed by this element.
import { SharedStyles } from "../shared-styles.js";

class LoginView extends PageViewElement {
  handleSubmit(event) {
    event.preventDefault();
    const data = {};
    new FormData(event.target).forEach(function(value, key) {
      data[key] = value;
    });
    data.password = sha256(data.password);
    const url = event.target.getAttribute("action");
    const json = JSON.stringify(data);
    fetch(url, {
      method: "post",
      body: json,
      credentials: "include",
      headers: {
        "content-type": "application/json"
      }
    });
  }
  render() {
    return html`
      ${SharedStyles}
      <section>
        <div>Login</div>
        <form
          action="http://localhost:8080/iea/rs/auth/login"
          @submit="${event => this.handleSubmit(event)}"
        >
          <input type="text" name="username" />
          <input type="password" name="password" />
          <input type="submit" value="Sign In" />
        </form>
      </section>
    `;
  }
}

window.customElements.define("login-view", LoginView);
