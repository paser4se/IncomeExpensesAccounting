import { html } from '@polymer/lit-element';
import { PageViewElement } from '../page-view-element.js';
import '../../js/dropzone.js';
import '@vaadin/vaadin-grid/vaadin-grid.js';

import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';
import '@polymer/iron-icons/av-icons.js';
import '@polymer/iron-icons/image-icons.js';

// These are the shared styles needed by this element.
import { DropZoneStyles } from '../dropzone-style';
import { SharedStyles } from "../shared-styles";
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
    this.position = 1;
    //this.setPayments("[]");
    //this.updatePayments();
  }

  render() {
    return html`
      ${DropZoneStyles}
      
      <style>
        #btnnext {
            float: right;
        }
        
        #btnback {
            float: left;
        }
        
        #cssmenu ul {
            display: flex; /* A key part of our menu, displays items side by side, and allows reversing them */
            flex-direction: row-reverse; /* Reverse the items */
        }
        #cssmenu-container {
            height: 220px;
            background-color: #CEE3F8;
        }
        
        /* Menu container */
        #cssmenu {
            display: inline-block; /* The menu will have width as needed, not 100% */
            position: relative;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            font-family: Helvetica, sans-serif;
            font-size: 14px;
            line-height: 1em;
            border-radius: 2px; /* Little curvature in the borders */
            overflow: hidden; /* Hide everything that overflows, like shadows */
        }
        
        /* Icons */
        #cssmenu i {
            transform: scale(1.4);
        }
        
        /* Menu */
        #cssmenu ul {
            display: flex; /* A key part of our menu, displays items side by side, and allows reversing them */
            flex-direction: row-reverse; /* Reverse the items */
            /* Reset styles, overwrite browser defaults */
            list-style: none;
            margin: 0;
            padding: 0;
        }
        
        #cssmenu ul li {
            margin: 0;
        }
        #cssmenu ul li a {
            display: inline-block;
            font-family: sans-serif;
            font-size: 0.9em;
            font-weight: 600;
            padding: 12px 30px 12px 45px; /* Adjusting padding to get the proper space */
            margin-left: -20px; /* Pull the items to the left, so the rounded right side will get over them */
            color: white;
            background-color: #288b9e;
            text-decoration: none;
            text-transform: uppercase;
            border-radius: 0 100px 100px 0; /* Get the right side rounded */
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.4); /* Apply the shadow */
        }
        
        /* Remove the shadow for first item, last in the right side when it gets reversed */
        #cssmenu ul li:first-child a {
            box-shadow: none;
        }
        
        /* Active item is a bit different */
        #cssmenu ul li.active a {
            color: #288b9e;
            background-color: #EEF5FF;
        }
        
        button::-moz-focus-inner { 
          border: 0; 
        }
        
      </style>
      ${SharedStyles}
      <link rel="stylesheet" href="../../../node_modules/bootstrap/dist/css/bootstrap.min.css">
      
      <div style="margin-top: 40px;">
          <div id="cssmenu">  
            <ul>
              <li><a left-item><iron-icon icon="image:edit"></iron-icon>Write off</a></li>
              <li><a left-item><iron-icon icon="av:playlist-add-check"></iron-icon>Check Category</a></li>
              <li class="active"><a left-item><iron-icon icon="cloud-upload"></iron-icon>Upload CSV</a></li>
            </ul>
          </div>
          
          <div id="pages">
            <div id="firstPage">
              <drop-zone></drop-zone>
            </div>
            <div id="secondPage" style="display: none;">
              <vaadin-grid theme="row-dividers" items="${this.payments}" style="min-height: 500px;" column-reordering-allowed multi-sort>
                <vaadin-grid-column width="8%" path="bookingDate"></vaadin-grid-column>
                <vaadin-grid-column width="8%" path="amount"></vaadin-grid-column>
                <vaadin-grid-column width="8%" path="currency"></vaadin-grid-column>
                <vaadin-grid-column width="68%" path="bookingText"></vaadin-grid-column>
                <vaadin-grid-column width="8%" path="valueDate"></vaadin-grid-column>
              </vaadin-grid>
            </div>
          </div>
          
          <div style="display: block; width: 50%; margin: auto; margin-top: 10px; position: fixed; bottom: 120px; left: 0; right: 0;">
            <button id="btnback" class="btn btn-primary" style="background-color: #288b9e !important; min-width: 100px;" disabled @click="${(evt) => this.handleBack(evt)}">Back</button>
            <button id="btnnext" class="btn btn-primary" style="background-color: #288b9e !important; min-width: 100px;" @click="${(evt) => this.handleNext(evt)}">Next</button>
          </div>
      </div>
    `;
  }

  handleNext(evt) {
    this.position++;
    var nextBtn = this.shadowRoot.querySelector('#btnnext');
    var backBtn = this.shadowRoot.querySelector('#btnback');
    var children = this.shadowRoot.querySelector('#cssmenu').children[0].children;

    if (this.position == 2) {
      this.updatePayments();
    }

    var pages = this.shadowRoot.querySelector('#pages').children;
    for(var i = 0; i < pages.length; i++){
      if (i == this.position-1){
        pages[i].style.display = 'block';
      } else {
        pages[i].style.display = 'none';
      }
    }

    if (this.position >= 3) {
      nextBtn.disabled = true;
    } else {
      nextBtn.disabled = false;
    }
    if (this.position >= 2) {
      backBtn.disabled = false;
    } else {
      backBtn.disabled = true;
    }

    var lastactive = false;
    for (var i = children.length-1; i >= 0; i--) {
      if (lastactive){
        children[i].classList.add('active');
        lastactive = false;
      } else {
        if (children[i].classList.contains('active')){
          children[i].classList.remove('active');
          lastactive = true;
        }
      }
    }
  }

  handleBack(evt) {
    this.position--;
    var nextBtn = this.shadowRoot.querySelector('#btnnext');
    var backBtn = this.shadowRoot.querySelector('#btnback');
    var children = this.shadowRoot.querySelector('#cssmenu').children[0].children;

    if (this.position == 2) {
      this.updatePayments();
    }

    var pages = this.shadowRoot.querySelector('#pages').children;
    for(var i = 0; i < pages.length; i++){
      if (i == this.position-1){
        pages[i].style.display = 'block';
      } else {
        pages[i].style.display = 'none';
      }
    }

    if (this.position >= 3) {
      nextBtn.disabled = true;
    } else {
      nextBtn.disabled = false;
    }
    if (this.position >= 2) {
      backBtn.disabled = false;
    } else {
      backBtn.disabled = true;
    }

    var lastactive = false;
    for (var i = 0; i < children.length; i++) {
      if (lastactive){
        children[i].classList.add('active');
        lastactive = false;
      } else {
        if (children[i].classList.contains('active')){
          children[i].classList.remove('active');
          lastactive = true;
        }
      }
    }
  }
}

window.customElements.define('payments-view', PaymentsView);