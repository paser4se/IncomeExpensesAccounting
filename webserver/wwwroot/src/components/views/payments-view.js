import { html } from '@polymer/lit-element';
import { PageViewElement } from '../page-view-element.js';
import '../../js/dropzone.js';
import '@vaadin/vaadin-grid/vaadin-grid.js';

// These are the shared styles needed by this element.
import { DropZoneStyles } from '../dropzone-style';
import 'bootstrap-table';

class PaymentsView extends PageViewElement {

  setPayments(pays){
    this.payments = pays;
  }

  static get properties() {
    return {
      payments:String
    }
  }

  updatePayments() {
    fetch('http://localhost:8080/iea/api/payments')
    .then(async function(response) {
      let tmp = await response.json();
      const payview = document.querySelector('iea-app').shadowRoot.children[3].querySelector('payments-view');
      payview.setPayments(JSON.stringify(tmp));
    });
  }

  constructor() {
    super();
    this.setPayments("[]");
    this.updatePayments();
  }

  render() {
    return html`
      ${DropZoneStyles}
      
      <section>
            <drop-zone></drop-zone>
            
            <button class="btn btn-default" @click="${this.updatePayments()}">Refresh</button>

            <vaadin-grid theme="row-dividers" items="${this.payments}" style="min-height: 500px;" column-reordering-allowed multi-sort>
              <vaadin-grid-column width="8%" path="bookingDate"></vaadin-grid-column>
              <vaadin-grid-column width="8%" path="amount"></vaadin-grid-column>
              <vaadin-grid-column width="8%" path="currency"></vaadin-grid-column>
              <vaadin-grid-column width="68%" path="bookingText"></vaadin-grid-column>
              <vaadin-grid-column width="8%" path="valueDate"></vaadin-grid-column>
            </vaadin-grid>
      </section>
    `;
  }
}

window.customElements.define('payments-view', PaymentsView);