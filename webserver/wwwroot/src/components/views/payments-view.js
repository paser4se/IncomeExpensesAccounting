import { html } from '@polymer/lit-element';
import { PageViewElement } from '../page-view-element.js';
import '../../js/dropzone.js';

// These are the shared styles needed by this element.
import { DropZoneStyles } from '../dropzone-style';
import 'bootstrap-table';

class PaymentsView extends PageViewElement {
  render() {
    return html`
      ${DropZoneStyles}
      
      <section>
            <drop-zone></drop-zone>

            <button class="btn btn-default" onclick="this.parentElement.querySelector('#paymentsTable').bootstrapTable('refresh')">Refresh</button>
            <table id="paymentsTable" data-toggle="table" data-url="http://localhost:8080/iea/rs/payments" class="table table-hover">
                <thead>
                <tr>
                    <th data-field="bookingDate" data-sortable="true">BOOKINGDATE</th>
                    <th data-field="amount" data-align="right" data-sortable="true">AMOUNT</th>
                    <th data-field="currency" data-sortable="true">CURRENCY</th>
                    <th data-field="bookingText" data-sortable="true">BOOKINGTEXT</th>
                    <th data-field="valueDate" data-sortable="true">VALUEDATE</th>
                </tr>
                </thead>
                <tbody id="gable">
                </tbody>
            </table>
      </section>
    `;
  }
}

window.customElements.define('payments-view', PaymentsView);
