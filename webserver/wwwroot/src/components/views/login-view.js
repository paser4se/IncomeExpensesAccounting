import { html } from "@polymer/lit-element";
import { PageViewElement } from "../page-view-element.js";

import "mdbootstrap";

class LoginView extends PageViewElement {
  handleSubmit(event, cb) {
    event.preventDefault();
    console.log(event);
    const data = {};
    new FormData(event.target).forEach(function(value, key) {
      data[key] = value;
    });

    console.log(data);

    cb(data);

    const url = event.target.getAttribute("action");
    const json = JSON.stringify(data);
    fetch(url, {
      method: 'POST',
      credentials: "include",
      headers: {
        "Content-Type": "text/plain"
      },
      body: json
    }).then(
        response => {
          console.log(response);
        }
    );
  }

  processRegister(data) {
    if (data.password != data.confirmPassword) {
      //password ungleich --> mit fehlermeldung reagieren
    }
    data.password = sha256(data.password);
  }

  processSignIn(data) {
    data.password = sha256(data.password);
  }

  render() {
    return html`
      <link
        rel="stylesheet"
        href="../../../node_modules/mdbootstrap/css/bootstrap.min.css"
      />
      <link
        rel="stylesheet"
        href="https://use.fontawesome.com/releases/v5.6.1/css/all.css"
      />
      <!-- Bootstrap core CSS -->
      <link href="css/bootstrap.min.css" rel="stylesheet" />
      <!-- Material Design Bootstrap -->
      <link href="css/mdb.min.css" rel="stylesheet" />
      <!-- Your custom styles (optional) -->
      <link href="css/style.css" rel="stylesheet" />
      <div>
        <div
          style="width: 50%; position: absolute; border-right: 1px solid #efefef;"
        >
          <form
            class="text-center p-5 needs-validation"
            novalidate
            method="post"
            action="http://localhost:8080/iea/api/auth/register"
            @submit="${event => this.handleSubmit(event, this.processRegister)}"
          >
            <p class="h4 mb-4">Sign up</p>

            <div class="form-row mb-4">
              <div class="col">
                <!-- First name -->
                <input
                  type="text"
                  name="firstName"
                  id="reg_firstname"
                  class="form-control"
                  placeholder="First name"
                  required
                />
              </div>
              <div class="col">
                <!-- Last name -->
                <input
                  type="text"
                  name="lastName"
                  id="reg_lastname"
                  class="form-control"
                  placeholder="Last name"
                  required
                />
              </div>
            </div>
            <input
              type="text"
              name="username"
              id="reg_username"
              class="form-control mb-4"
              placeholder="Username"
              required
            />
            <div class="invalid-feedback">Please enter a valid username.</div>
            <input
              type="email"
              name="email"
              id="reg_email"
              class="form-control mb-4"
              placeholder="E-mail"
              required
            />
            <div class="invalid-feedback">Please enter a valid email.</div>
            <input
              type="password"
              name="password"
              id="reg_password"
              class="form-control mb-4"
              placeholder="Password"
              required
            />
            <div class="invalid-feedback">Please enter a valid password</div>
            <input
              type="password"
              name="confirmPassword"
              id="reg_confpassword"
              class="form-control mb-4"
              placeholder="Confirm Password"
              required
            />
            <div class="invalid-feedback">Retype password again.</div>
            <!-- Newsletter -->
            <div class="custom-control custom-checkbox">
              <input
                type="checkbox"
                name="newsletter"
                class="custom-control-input"
                id="reg_newsletter"
              />
              <label class="custom-control-label" for="reg_newsletter"
                >Subscribe to our newsletter</label
              >
            </div>

            <input
              class="btn btn-info my-4 btn-block"
              type="submit"
              value="Sign up"
            />
            <hr />

            <p>
              By clicking <em>Sign up</em> you agree to our
              <a href="" target="_blank">terms of service</a>
            </p>
          </form>
        </div>
        <div
          style="width: 50%; left: 0; right: 0; display: inline-block; float: right;"
        >
          <form
            class="text-center p-5"
            method="post"
            action="http://localhost:8080/iea/api/auth/login"
            @submit="${event => this.handleSubmit(event, this.processSignIn)}"
          >
            <p class="h4 mb-4">Sign in</p>
            <input
              type="email"
              id="login_email"
              class="form-control mb-4"
              placeholder="E-mail or Username"
            />
            <input
              type="password"
              id="login_password"
              class="form-control mb-4"
              placeholder="Password"
            />

            <input
              class="btn btn-info btn-block my-4"
              type="submit"
              value="Sign in"
            />
          </form>
        </div>
      </div>
    `;
  }
}

window.customElements.define("login-view", LoginView);
