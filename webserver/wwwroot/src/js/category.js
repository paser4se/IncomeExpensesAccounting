import {LitElement, html} from "@polymer/lit-element";
import 'https://code.jquery.com/jquery-3.3.1.min.js';
import '@vaadin/vaadin-grid/vaadin-grid.js';

class Category extends LitElement {

    constructor() {
        super();
        this.currentCategory = -1;
        this.categories = [];
    }

    static get properties() {
        return {
            currentCategory: { type: Number },
            categories: { type: String }
        };
    }

    render() {
        return html`
            <style>
                section {
                  position: relative;
                  background-color: #fefefe;
                  margin: auto;
                  padding: 0;
                  border: 1px solid #888;
                  width: 80%;
                  margin-top: 20px;
                  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19);
                  -webkit-animation-name: animatetop;
                  -webkit-animation-duration: 0.4s;
                  animation-name: animatetop;
                  animation-duration: 0.4s;
                }
                
                /* Add Animation */
                @-webkit-keyframes animatetop {
                  from {top:-300px; opacity:0} 
                  to {top:0; opacity:1}
                }
                
                @keyframes animatetop {
                  from {top:-300px; opacity:0}
                  to {top:0; opacity:1}
                }
                
                /* The Close Button */
                .close {
                  color: white;
                  float: right;
                  font-size: 28px;
                  font-weight: bold;
                }
                
                .close:hover,
                .close:focus {
                  color: #000;
                  text-decoration: none;
                  cursor: pointer;
                }
                
                .modal-header {
                  padding: 2px 16px;
                  background-color: #288b9e;
                  color: white;
                }
                
                .modal-body {padding: 2px 16px;}
                
                .grid {
                    width: 240px;
                }
                
                .grid-container {
                  display: grid;
                  grid-gap: 50px;
                  grid-template-columns: 280px auto;
                  padding: 10px;
                }
                
                .grid-item {
                  padding: 20px;
                  font-size: 30px;
                }
            </style>

            <section>
                <div class="modal-header">
                  <span @click="${(evt) => this.close(evt)}" class="close">&times;</span>
                  <h2>Change Category</h2>
                </div>
                <div class="modal-body">
                  <div class="grid-container">
                      <div class="grid-item">
                        <vaadin-grid id="grid" style="box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19)" class="grid" theme="row-dividers" items="${this.categories}">
                          <vaadin-grid-column path="text" header="Categories"></vaadin-grid-column>
                        </vaadin-grid>
                      </div>
                      <div class="grid-item">
                        <p>Some text in the Modal Body</p>
                        <p>Category ${this.currentCategory}</p>
                        <button class="btn btn-primary" @click="${(evt) => this.loadContent(evt)}">Load</button>
                      </div>
                  </div>
                </div>
            </section>
        `;
    }

    loadContent(evt) {
        var tree = [
            {
                text: "Parent 1"
            },
            {
                text: "Parent 2"
            },
            {
                text: "==>  Parent 3"
            },
            {
                text: "Parent 4"
            },
            {
                text: "Parent 5"
            }
        ];
        this.categories = JSON.stringify(tree);

        var grid = this.shadowRoot.querySelector('#grid');
        grid.addEventListener('active-item-changed', function(event) {
            const item = event.detail.value;
            if (item) {
                grid.selectedItems = [item];
            }
        });
        //$(this.shadowRoot.querySelector('#grid')).treeview({data: tree, levels: 2});
        //$('#tree').treeview('selectNode', [ 3, { silent: true } ]);
    }

    close(evt) {


        if (typeof jQuery == 'undefined') {

            // jQuery IS NOT loaded, do stuff here.
            console.log('disabled');
        }
        else{
            console.log('enabled');
        }
        this.style.display = "none";
    }
}

window.customElements.define('category-view', Category);