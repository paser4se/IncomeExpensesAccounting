import { LitElement, html } from "@polymer/lit-element";
import PaymentsView from "./payments.js";

class ContentView extends LitElement {
  constructor() {
    super();
    this.oldChild = null;
  }

  changeInner(id) {
    //console.log(id);
    let view = null;

    if (id === "nav_payments") {
      view = new PaymentsView();
    }

    if (this.oldChild) {
      this.shadowRoot.replaceChild(view, this.oldChild);
    } else {
      this.shadowRoot.appendChild(view);
    }

    this.oldChild = view;
  }
}

customElements.define("content-view", ContentView);
