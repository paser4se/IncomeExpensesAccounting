import { html } from "@polymer/lit-element";
import { PageViewElement } from "../page-view-element.js";

import { SharedStyles } from "../shared-styles";

class EvaluationView extends PageViewElement {
  render() {
    return html`
      ${SharedStyles}

      <section>
        <div>Evaluation</div>
        <div id="container" style="width: 100%; height: 100%"></div>
      </section>
    `;
  }
}

window.customElements.define("evaluation-view", EvaluationView);
anychart.onDocumentReady(function() {
  // dummy werte
  var data = [
    { x: "Gehalt", value: 10000 },
    { x: "Sonstiges", value: 4500 },
    { x: "Bildung", value: 300 },
    { x: "Wohnen", value: 2500 },
    { x: "Kleidung", value: 150 },
    { x: "Essen", value: 1500 },
    { x: "Auto", value: 1000 }
  ];

  // create the chart
  var chart = anychart.pie();

  // set the chart title
  chart.title("Auswertung Ihrer Zahlungen");

  // add the data
  chart.data(data);

  // display the chart in the container
  chart.container("container");
  chart.draw();
});
