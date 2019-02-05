import { html } from '@polymer/lit-element';
import { PageViewElement } from '../page-view-element.js';
import '../../js/dropzone.js';
import '@vaadin/vaadin-grid/vaadin-grid.js';

// These are the shared styles needed by this element.
import { DropZoneStyles } from '../dropzone-style';
import 'bootstrap-table';

class PaymentsView extends PageViewElement {

  updateItems() {
    console.log('updating');
    console.log(this.shadowRoot.children);
    console.log(this.shadowRoot.children.length);
    console.log('=============');

    for (var i = 0; i < this.shadowRoot.children.length; i++){
      var grid = this.shadowRoot.children[i].querySelector('vaadin-grid');
      console.log(this.shadowRoot.children[i]);
      if (grid != null){
        grid.dataProvider = function(params, callback) {
          var url = 'http://localhost:8080/iea/api/payments';
          var xhr = new XMLHttpRequest();
          xhr.onload = function() {
            var response = JSON.parse(xhr.responseText);
            console.log(response);
            callback(
                response,
                response.length
            );
          };
          xhr.open('GET', url, true);
          xhr.send();
        }
      }
    }
  }

  render() {
    return html`
      ${DropZoneStyles}
      
      <section>
            <drop-zone></drop-zone>
            
            <button class="btn btn-default" @click="${evt => this.updateItems()}">Refresh</button>

            <vaadin-grid theme="row-dividers" style="min-height: 500px;" column-reordering-allowed multi-sort>
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