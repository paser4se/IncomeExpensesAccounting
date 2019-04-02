import { html } from "@polymer/lit-element";
import { PageViewElement } from "../page-view-element.js";

import { SharedStyles } from "../shared-styles";

import '@google-web-components/google-chart/google-chart.js';

import { PolymerElement, html } from "@polymer/polymer";
import "@google-web-components/google-chart/google-chart.js";

class EvaluationView extends PageViewElement {

  render() {
    return html`
      <style>
      
      </style>
      ${SharedStyles}

<<<<<<< HEAD
      <google-chart data='[["Month", "Days"], ["Jan", 31]]'></google-chart>
=======
      <section>
        <div>Evaluation</div>
        <google-chart
          type='pie'
          options='{"width": "400", "height": "400", "title": "Test Chart", "chartArea": {"width": "300", "height": "300"}, "legend": "none"}'
          cols='[{"label":"Month", "type":"string"}, {"label":"Days", "type":"number"}]'
          rows='[["Jan", 31],["Feb", 28],["Mar", 31]]'>
        </google-chart>
      </section>
>>>>>>> 17da7fe25e74f4dfc8a85a4e2649d019ae3a7d82
    `;
  }

}

window.customElements.define("evaluation-view", EvaluationView);
