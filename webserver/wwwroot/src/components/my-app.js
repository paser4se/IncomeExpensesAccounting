/**
@license
Copyright (c) 2018 The Polymer Project Authors. All rights reserved.
This code may only be used under the BSD style license found at http://polymer.github.io/LICENSE.txt
The complete set of authors may be found at http://polymer.github.io/AUTHORS.txt
The complete set of contributors may be found at http://polymer.github.io/CONTRIBUTORS.txt
Code distributed by Google as part of the polymer project is also
subject to an additional IP rights grant found at http://polymer.github.io/PATENTS.txt
*/

import { LitElement, html } from "@polymer/lit-element";
import { setPassiveTouchGestures } from "@polymer/polymer/lib/utils/settings.js";
import { connect } from "pwa-helpers/connect-mixin.js";
import { installMediaQueryWatcher } from "pwa-helpers/media-query.js";
import { installOfflineWatcher } from "pwa-helpers/network.js";
import { installRouter } from "pwa-helpers/router.js";
import { updateMetadata } from "pwa-helpers/metadata.js";
import { createStore } from "redux";

import "@polymer/iron-icon/iron-icon.js";
import "@polymer/iron-icons/iron-icons.js";

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
      <style>
        :host {
          --app-drawer-width: 256px;
          display: block;

          --app-primary-color: #288b9e;
          --app-secondary-color: rgb(41, 50, 55);
          --app-dark-text-color: var(--app-secondary-color);
          --app-light-text-color: white;
          --app-section-even-color: #f7f7f7;
          --app-section-odd-color: white;

          --app-header-background-color: white;
          --app-header-text-color: var(--app-dark-text-color);
          --app-header-selected-color: var(--app-primary-color);

          --app-drawer-background-color: var(--app-secondary-color);
          --app-drawer-text-color: var(--app-light-text-color);
          --app-drawer-selected-color: #288b9e;
        }

        app-header {
          position: fixed;
          top: 0;
          left: 0;
          width: 100%;
          text-align: center;
          background-color: var(--app-header-background-color);
          color: var(--app-header-text-color);
          border-bottom: 1px solid #eee;
        }

        .toolbar-top {
          background-color: var(--app-header-background-color);
        }

        [main-title] {
          color: var(--app-primary-color);
          padding-top: 20px;
          font-family: "Dyuthi";
          font-weight: bold;
          font-size: 80px;
          /* In the narrow layout, the toolbar is offset by the width of the
        drawer button, and the text looks not centered. Add a padding to
        match that button */
          padding-right: 44px;
        }

        [left-item] {
          float: left !important;
        }

        [right-item] {
          float: right !important;
        }

        .toolbar-list {
          display: none;
          text-align: left !important;
        }

        .toolbar-list > a {
          display: inline-block;
          color: var(--app-header-text-color);
          text-decoration: none;
          line-height: 30px;
          padding: 4px 24px;
          outline: 0;
        }

        .toolbar-list > a[selected] {
          /*color: var(--app-header-selected-color);*/
          border-bottom: 4px solid var(--app-header-selected-color);

          outline: 0;
        }

        .menu-btn {
          background: none;
          border: none;
          fill: var(--app-header-text-color);
          cursor: pointer;
          height: 44px;
          width: 44px;
        }

        .drawer-list {
          box-sizing: border-box;
          width: 100%;
          height: 100%;
          padding: 24px;
          background: var(--app-drawer-background-color);
          position: relative;
        }

        .drawer-list > a {
          display: block;
          text-decoration: none;
          color: var(--app-drawer-text-color);
          line-height: 40px;
          padding: 0 24px;
          outline: 0;
        }

        .drawer-list > a[selected] {
          color: var(--app-drawer-selected-color);
          outline: 0;
        }

        /* Workaround for IE11 displaying <main> as inline */
        main {
          display: block;
        }

        .main-content {
          padding-top: 64px;
          min-height: calc(100vh - 64px - 104px);
        }

        .page {
          display: none;
        }

        .page[active] {
          display: block;
        }

        footer {
          padding: 24px;
          background: var(--app-drawer-background-color);
          color: var(--app-drawer-text-color);
          text-align: center;
        }

        nav.pullUp a:before,
        nav.pullUp div.profile:before {
          position: absolute;
          width: 0px;
          height: 0px;
          left: 0px;
          bottom: 0px;
          content: "";
          background: var(--app-header-selected-color);
          opacity: 0.3;
          transition: all 0.3s;
        }

        nav.pullUp a:hover:before,
        nav.pullUp div.profile:hover:before {
          width: 100%;
          height: 2px;
        }

        nav div.profile:hover div.dropdown-content {
          display: block;
        }

        nav.container {
          /*font-family: Raleway;*/
          margin: 0 auto;
          /*padding: 10em 3em;*/
          text-align: center;
        }

        .profile {
          position: relative;
        }

        nav.container a,
        nav.container div {
          color: var(--app-header-text-color);
          text-decoration: none;
          font: 20px Ubuntu;
          margin: 0px 10px;
          padding: 10px 10px;
          position: relative;
          z-index: 0;
          cursor: pointer;
        }

        .dropdown-content {
          display: none;
          position: absolute !important;
          background-color: #f9f9f9;
          margin-top: 25px;
          min-width: 160px;
          right: 0;
          box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
          z-index: 1;
        }

        .dropdown-content a,
        .dropdown-content div {
          color: black;
          padding: 12px 16px;
          text-decoration: none;
          display: block;
        }

        /* Wide layout: when the viewport width is bigger than 460px, layout
      changes to a wide layout. */
        @media (min-width: 460px) {
          .toolbar-list {
            display: block;
          }

          .menu-btn {
            display: none;
          }

          .main-content {
            padding-top: 107px;
            min-height: calc(100vh - 212px);
          }

          /* The drawer button isn't shown in the wide layout, so we don't
        need to offset the title */
          [main-title] {
            padding-right: 0px;
          }
        }
      </style>

      <!-- Header -->
      <app-header condenses reveals effects="waterfall">
        <app-toolbar class="toolbar-top">
          <button
            class="menu-btn"
            title="Menu"
            @click="${this._menuButtonClicked}"
          >
            ${menuIcon}
          </button>
          <div main-title>${this.appTitle}</div>
        </app-toolbar>

        <!-- This gets hidden on a small screen-->
        <nav class="toolbar-list container pullUp">
          <a ?selected="${this._page === "Home"}" href="/Home" left-item
            ><iron-icon icon="home"></iron-icon>Home</a
          >
          <a ?selected="${this._page === "Payments"}" href="/Payments" left-item
            >Payments</a
          >
          <a ?selected="${this._page === "Overview"}" href="/Overview" left-item
            >Overview</a
          >
          <a
            ?selected="${this._page === "Evaluation"}"
            href="/Evaluation"
            left-item
            >Evaluation</a
          >
          <a ?selected="${this._page === "Login"}" href="/Login" right-item
            >Sign In/Sign Up</a
          >
          <div class="profile" right-item>
            Profile
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
            >Payments</a
          >
          <a ?selected="${this._page === "Overview"}" href="/Overview" left-item
            >Overview</a
          >
          <a
            ?selected="${this._page === "Evaluation"}"
            href="/Evaluation"
            left-item
            >Evaluation</a
          >
          <a ?selected="${this._page === "Login"}" href="/Login" right-item
            >Sign In/Sign Up</a
          >
          <div class="profile" right-item>
            Profile
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
          ?active="${this._page === "Login"}"
        ></login-view>
        <my-view404
          class="page"
          ?active="${this._page === "view404"}"
        ></my-view404>
      </main>

      <footer>
        <p>Footer</p>
      </footer>

      <snack-bar ?active="${this._snackbarOpened}">
        You are now ${this._offline ? "offline" : "online"}.</snack-bar
      >
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

    fetch("http://localhost:8080/iea/rs/auth/authenticate", {
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
