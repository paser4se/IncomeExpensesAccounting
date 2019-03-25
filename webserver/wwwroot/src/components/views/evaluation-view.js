import { html } from "@polymer/lit-element";
import { PageViewElement } from "../page-view-element.js";

import { SharedStyles } from "../shared-styles";

import Chart from "chart.js";

class EvaluationView extends PageViewElement {
  render() {
    return html`
      ${SharedStyles}

      <section>
        <div>Evaluation</div>
        <canvas id="pie-chart" width="800" height="450"></canvas>
      </section>
    `;
  }
  connectedCallback() {
    console.log(Chart);
    new Chart(this.shadowRoot.getElementById("pie-chart"), {
      type: "pie",
      data: {
        labels: ["Africa", "Asia", "Europe", "Latin America", "North America"],
        datasets: [
          {
            label: "Population (millions)",
            backgroundColor: [
              "#3e95cd",
              "#8e5ea2",
              "#3cba9f",
              "#e8c3b9",
              "#c45850"
            ],
            data: [2478, 5267, 734, 784, 433]
          }
        ]
      },
      options: {
        title: {
          display: true,
          text: "Predicted world population (millions) in 2050"
        }
      }
    });
  }
}

window.customElements.define("evaluation-view", EvaluationView);
