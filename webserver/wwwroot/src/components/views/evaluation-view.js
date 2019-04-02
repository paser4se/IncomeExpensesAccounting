import { html } from "@polymer/lit-element";
import { PageViewElement } from "../page-view-element.js";

import { SharedStyles } from "../shared-styles";

import "@google-web-components/google-chart/google-chart.js";

class EvaluationView extends PageViewElement {
  render() {
    return html`
      <style></style>
      ${SharedStyles}

      <section>
        <google-chart
          type="pie"
          options='{"width": "400", "height": "400", "title": "Test Chart", "chartArea": {"width": "300", "height": "300"}, "legend": "none"}'
          cols='[{"label":"Month", "type":"string"}, {"label":"Days", "type":"number"}]'
          rows='[["Jan", 31],["Feb", 28],["Mar", 31]]'
        >
        </google-chart>
      </section>
    `;
  }
}

window.customElements.define("evaluation-view", EvaluationView);
