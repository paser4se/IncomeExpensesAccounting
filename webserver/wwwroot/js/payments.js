import {LitElement, html} from "@polymer/lit-element";

export default class PaymentsView extends LitElement {

    constructor() {
        super();
    }

    render() {
        return html`
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.12.2/bootstrap-table.min.css"></script>
  <script src="https://unpkg.com/@webcomponents/webcomponentsjs@2.0.0/webcomponents-loader.js"></script>
  <script src="https://unpkg.com/wired-elements@latest/dist/wired-elements.bundled.min.js"></script>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.4/umd/popper.min.js"></script>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.5.16/js/mdb.min.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.5.16/css/mdb.min.css" rel="stylesheet">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/js/bootstrap.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.10.1/bootstrap-table.min.js"></script>
  <style>
                @import "../style/dropzone.css";
            </style>
            <drop-zone></drop-zone>

            <!--<button class="btn btn-default" onclick="$('#paymentsTable').bootstrapTable('refresh')">Refresh</button>-->
            <table id="paymentsTable" data-toggle="table" data-url="http://localhost:8085/iea/rs/payments"  class="table table-hover">
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
        `;
    }

}

customElements.define('payment-view', PaymentsView);