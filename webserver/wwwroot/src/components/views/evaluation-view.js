import { html } from "@polymer/lit-element";
import { PageViewElement } from "../page-view-element.js";

import { SharedStyles } from "../shared-styles";

import "@google-web-components/google-chart/google-chart.js";

const rowValues = [["Gehalt", 31], ["Auto", 28], ["Wohnen", 31]]; // momentan noch dummy data

class EvaluationView extends PageViewElement {
  render() {
    return html`
      <style></style>
      ${SharedStyles}

      <section>
        <google-chart
          type="pie"
          options='{"width": "400", "height": "400", "title": "Einnahmen & Ausgaben", "chartArea": {"width": "300", "height": "300"}, "legend": "none"}'
          cols='[{"label":"Category", "type":"string"}, {"label":"Amount", "type":"number"}]'
          rows=${JSON.stringify(rowValues)}
        >
        </google-chart>
      </section>
    `;
  }
}

window.customElements.define("evaluation-view", EvaluationView);
