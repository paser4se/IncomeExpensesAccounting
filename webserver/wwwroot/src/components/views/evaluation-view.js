import { html } from "@polymer/lit-element";
import { PageViewElement } from "../page-view-element.js";

import { SharedStyles } from "../shared-styles";

import '@google-web-components/google-chart/google-chart.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';

class EvaluationView extends PageViewElement {

  render() {
    return html`
      <style>
        .grid-container {
          padding: 24px;
          display: grid;
          grid-gap: 15px;
          grid-template-columns: 20% 45% auto;
          grid-template-rows: 550px auto;
          grid-template-areas:
          "filter chart table"
          "expanded expanded expanded"
        }
        
        .header {
          background-color: #288b9e;
          height: 50px;
          display: flex; 
          display: -webkit-flex;
          vertical-align: center;
        }
        
        .header-text {
          text-align: center; 
          margin: auto;
          color: white;
          font-weight: bold;
        }
        
        .windowcontent {
          
        }
        
        #filterWindow {
          grid-area: filter;
          box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19);
        }
        
        #chart {
          grid-area: chart;
        }
        
        #table {
          grid-area: table;
          background-color: #39bfd3;
        }
        
        #expanded {
          grid-area: expanded;
          background-color: goldenrod;
        }
        
        .collapsible {
          background-color: #777;
          color: white;
          cursor: pointer;
          padding: 18px;
          width: 100%;
          border: none;
          text-align: left;
          outline: none;
          font-size: 15px;
        }
        
        .active, .collapsible:hover {
          background-color: #555;
        }
        
        .content {
          padding: 0 18px;
          max-height: 0;
          overflow: hidden;
          transition: max-height 0.2s ease-out;
          background-color: #f1f1f1;
        }
        
        button::-moz-focus-inner {
          border: 0;
        }
      </style>
      ${SharedStyles}

      <div style="height: auto;">
        <div class="grid-container">
          <div id="filterWindow">
              <div class="header"><label class="header-text">FILTER</label></div>
              <div class="windowcontent">
                <label>Comming soon....</label>
              </div>
          </div>
          <div id="chart">
          <google-chart type='pie'
            options='{"width": "400", "height": "400", "title": "Test Chart", "chartArea": {"width": "300", "height": "300"}, "legend": "none"}'
            cols='[{"label":"Month", "type":"string"}, {"label":"Days", "type":"number"}]'
            rows='[["Jan", 31],["Feb", 28],["Mar", 31]]'>
          </google-chart>
          </div>
          <div id="table">
              <label>TABLE</label>
          </div>
          
          <div id="expanded">
            <button class="collapsible" @click="${(evt) => this.toggle(evt)}">
                <iron-icon id="icon_down" icon="arrow-drop-down"></iron-icon>
                <iron-icon id="icon_up" icon="arrow-drop-up" style="display: none;"></iron-icon> Erweitert</button>
             <div class="content">
               <p>This is a placeholder....</p>
             </div>
          </div>
        </div>
      </div>
    `;
  }

  toggle(evt) {
    var btn = this.shadowRoot.querySelector('.collapsible');
    var icon_down = this.shadowRoot.querySelector('#icon_down');
    var icon_up = this.shadowRoot.querySelector('#icon_up');
    btn.classList.toggle("active");
    if (btn.classList.contains("active")) {
      icon_down.style.display = "none";
      icon_up.style.display = "inherit";
    } else {
      icon_up.style.display = "none";
      icon_down.style.display = "inherit";
    }
    var content = btn.nextElementSibling;
    if (content.style.maxHeight){
      content.style.maxHeight = null;
    } else {
      content.style.maxHeight = content.scrollHeight + "px";
    }
  }
}

window.customElements.define("evaluation-view", EvaluationView);
