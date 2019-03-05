/**
@license
Copyright (c) 2018 The Polymer Project Authors. All rights reserved.
This code may only be used under the BSD style license found at http://polymer.github.io/LICENSE.txt
The complete set of authors may be found at http://polymer.github.io/AUTHORS.txt
The complete set of contributors may be found at http://polymer.github.io/CONTRIBUTORS.txt
Code distributed by Google as part of the polymer project is also
subject to an additional IP rights grant found at http://polymer.github.io/PATENTS.txt
*/

/*import { LitElement, html } from "@polymer/lit-element";
import { setPassiveTouchGestures } from "@polymer/polymer/lib/utils/settings.js";
import { connect } from "pwa-helpers/connect-mixin.js";
import { installMediaQueryWatcher } from "pwa-helpers/media-query.js";
import { installOfflineWatcher } from "pwa-helpers/network.js";
import { installRouter } from "pwa-helpers/router.js";
import { updateMetadata } from "pwa-helpers/metadata.js";
import { createStore } from "redux";*/

import "@polymer/iron-icon/iron-icon.js";
import "@polymer/iron-icons/iron-icons.js";
import { LitElement, html } from "@polymer/lit-element";
import { setPassiveTouchGestures } from "@polymer/polymer/lib/utils/settings.js";
import { connect } from "pwa-helpers/connect-mixin.js";
import { installMediaQueryWatcher } from "pwa-helpers/media-query.js";
import { installOfflineWatcher } from "pwa-helpers/network.js";
import { installRouter } from "pwa-helpers/router.js";
import { updateMetadata } from "pwa-helpers/metadata.js";
import { FooterStyle } from "./footer-style";
import { AppStyle } from "./app-style";

import "@polymer/iron-icon/iron-icon.js";
import "@polymer/iron-icons/iron-icons.js";
import "@polymer/iron-icons/editor-icons.js";
import "@polymer/iron-icons/social-icons.js";

// This element is connected to the Redux store.
import { store } from "../store.js";

// These are the actions needed by this element.
import { navigate, updateOffline, updateDrawerState } from "../actions/app.js";

// These are the elements needed by this element.
import "@polymer/app-layout/app-drawer/app-drawer.js";
import "@polymer/app-layout/app-header/app-header.js";
import "@polymer/app-layout/app-scroll-effects/effects/waterfall.js";
import "@polymer/app-layout/app-toolbar/app-toolbar.js";
import { menuIcon } from "./my-icons.js";

class IEA extends connect(store)(LitElement) {
  render() {
    // Anything that's related to rendering should be done in here.
    return html`
      ${AppStyle} ${FooterStyle}
      <!-- Header -->
      <app-header fixed effects="waterfall">
        <app-toolbar class="toolbar-top">
          <button
            class="menu-btn"
            title="Menu"
            @click="${this._menuButtonClicked}"
          >
            ${menuIcon}
          </button>
          <div main-title>
            <img src="../../images/Logo.png" style="height: 50px;" />${this
              .appTitle}
          </div>
        </app-toolbar>
        <!-- This gets hidden on a small screen-->
        <nav class="toolbar-list container pullUp">
          <a ?selected="${this._page === "Home"}" href="/Home" left-item
            ><iron-icon icon="home"></iron-icon>Home</a
          >
          <a ?selected="${this._page === "Payments"}" href="/Payments" left-item
            ><iron-icon icon="credit-card"></iron-icon>Payments</a
          >
          <a ?selected="${this._page === "Overview"}" href="/Overview" left-item
            ><iron-icon icon="inbox"></iron-icon>Overview</a
          >
          <a
            ?selected="${this._page === "Evaluation"}"
            href="/Evaluation"
            left-item
            ><iron-icon icon="editor:insert-chart"></iron-icon>Evaluation</a
          >
          <a ?selected="${this._page === "Login"}" href="/Login" right-item
            >Sign In/Sign Up</a
          >
          <div class="profile" right-item>
            <iron-icon icon="social:person"></iron-icon>Profile
            <div class="dropdown-content">
              <a href="#">Link 1</a>
              <a href="#">Link 2</a>
              <a href="#">Link 3</a>
            </div>
          </div>
        </nav>
      </app-header>
      <!-- Drawer content -->
      <app-drawer
        .opened="${this._drawerOpened}"
        @opened-changed="${this._drawerOpenedChanged}"
      >
        <nav class="drawer-list container pullUp">
          <a ?selected="${this._page === "Home"}" href="/Home" left-item
            ><iron-icon icon="home"></iron-icon>Home</a
          >
          <a ?selected="${this._page === "Payments"}" href="/Payments" left-item
            ><iron-icon icon="credit-card"></iron-icon>Payments</a
          >
          <a ?selected="${this._page === "Overview"}" href="/Overview" left-item
            ><iron-icon icon="inbox"></iron-icon>Overview</a
          >
          <a
            ?selected="${this._page === "Evaluation"}"
            href="/Evaluation"
            left-item
            ><iron-icon icon="editor:insert-chart"></iron-icon>Evaluation</a
          >
          <a ?selected="${this._page === "Login"}" href="/Login" left-item
            >Sign In/Sign Up</a
          >
          <div class="profile" left-item>
            <iron-icon icon="social:person"></iron-icon>Profile
            <div class="dropdown-content">
              <a href="#">Link 1</a>
              <a href="#">Link 2</a>
              <a href="#">Link 3</a>
            </div>
          </div>
        </nav>
      </app-drawer>
      <!-- Main content -->
      <main role="main" class="main-content">
        <home-view class="page" ?active="${this._page === "Home"}"></home-view>
        <payments-view
          class="page"
          ?active="${this._page === "Payments"}"
        ></payments-view>
        <overview-view
          class="page"
          ?active="${this._page === "Overview"}"
        ></overview-view>
        <evaluation-view
          class="page"
          ?active="${this._page === "Evaluation"}"
        ></evaluation-view>
        <login-view
          class="page"
          style="min-height: 630px;"
          ?active="${this._page === "Login"}"
        ></login-view>
        <my-view404
          class="page"
          ?active="${this._page === "view404"}"
        ></my-view404>
      </main>
      <footer class="footer-distributed">
        <div class="footer-left">
          <div style="display: -webkit-inline-flex;">
            <img src="../../images/Logo.png" /><span>IEA</span>
          </div>
          <p class="footer-company-name">IEA &copy; 2019</p>
        </div>

        <div class="footer-right">
          <p>Contact Us:</p>
          <form action="#" method="post">
            <input type="text" name="email" placeholder="Email" />
            <textarea name="message" placeholder="Message"></textarea>
            <button>Send</button>
          </form>
        </div>
      </footer>
    `;
  }

  static get properties() {
    return {
      appTitle: { type: String },
      _page: { type: String },
      _drawerOpened: { type: Boolean },
      _snackbarOpened: { type: Boolean },
      _offline: { type: Boolean }
    };
  }

  constructor() {
    super();
    // To force all event listeners for gestures to be passive.
    // See https://www.polymer-project.org/3.0/docs/devguide/settings#setting-passive-touch-gestures
    // TODO: handle result

    fetch("http://localhost:8080/iea/api/auth/authenticate", {
      method: "post",
      credentials: "include"
    }).then(res => {
      if (res.status == 404) {
        // account has not been created --> register-view
      } else if (res.status == 200) {
        // account has been created --> login-view
      } else if (res.status == 400) {
        // cookie empty --> register-view
      }
    });
    setPassiveTouchGestures(true);
  }

  firstUpdated() {
    installRouter(location =>
      store.dispatch(navigate(decodeURIComponent(location.pathname)))
    );
    installOfflineWatcher(offline => store.dispatch(updateOffline(offline)));
    installMediaQueryWatcher(`(min-width: 460px)`, () =>
      store.dispatch(updateDrawerState(false))
    );
  }

  updated(changedProps) {
    if (changedProps.has("_page")) {
      const pageTitle = this.appTitle + " - " + this._page;
      updateMetadata({
        title: pageTitle,
        description: pageTitle
        // This object also takes an image property, that points to an img src.
      });
    }
  }

  _menuButtonClicked() {
    store.dispatch(updateDrawerState(true));
  }

  _drawerOpenedChanged(e) {
    store.dispatch(updateDrawerState(e.target.opened));
  }

  stateChanged(state) {
    this._page = state.app.page;
    this._offline = state.app.offline;
    this._snackbarOpened = state.app.snackbarOpened;
    this._drawerOpened = state.app.drawerOpened;
  }
}

window.customElements.define("iea-app", IEA);
