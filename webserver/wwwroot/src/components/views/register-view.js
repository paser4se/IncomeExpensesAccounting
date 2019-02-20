import { html } from "@polymer/lit-element";
import { PageViewElement } from "../page-view-element.js";

// These are the shared styles needed by this element.
import { SharedStyles } from "../shared-styles.js";

class RegisterView extends PageViewElement {
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
        <div>Sign Up</div>
        <form
          action="http://localhost:8080/iea/rs/auth/register"
          @submit="${event => this.handleSubmit(event)}"
        >
          <input type="text" name="username" value="winter_is_coming" />
          <input type="email" name="email" value="jonsnow@winterfell.got" />
          <input type="text" name="fullname" value="Jon Snow" />
          <input type="password" name="password" value="Enter Password" />
          <input
            type="password"
            name="passwordretype"
            value="Retype Password"
          />
          <input type="submit" value="Sign Up" />
        </form>
      </section>
    `;
  }
}

window.customElements.define("register-view", RegisterView);
