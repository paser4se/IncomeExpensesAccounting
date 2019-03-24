import {LitElement, html} from "@polymer/lit-element";
import 'https://code.jquery.com/jquery-3.3.1.min.js';
import '@vaadin/vaadin-grid/vaadin-grid.js';
import { DropZoneStyles } from '../components/dropzone-style.js';

import '@vaadin/vaadin-button/vaadin-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/paper-input/paper-input.js';
import 'mdbootstrap';
import '@vaadin/vaadin-checkbox/vaadin-checkbox.js';

class Category extends LitElement {

    constructor() {
        super();
        this.currentCategory = -1;
        this.categories = [];
    }

    static get properties() {
        return {
            currentCategory: { type: Number },
            currentPayment: { type: Number },
            categories: { type: String },
            keywords: { type: String }
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
                  width: 30%;
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
                  color: white !important;
                  opacity: 1 !important;
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
                  border-radius: 0px !important;
                }
                
                /*.modal-body {padding: 2px 16px;}*/
                
                .grid {
                    width: 240px;
                    margin-left: auto;
                    margin-right: auto;
                }
                
                .grid-container {
                  display: grid;
                  grid-gap: 15px;
                  grid-template-columns: auto auto;
                  grid-template-rows: auto 38px 46px;
                  grid-template-areas:
                  "grida gridb"
                  "inputa inputb"
                  "subcat inputb"
                  "buttona buttonb"
                }
                
                .submitBtn {
                    width: 86%;
                    position: absolute !important;
                    display: inline;
                    margin-left: auto;
                    margin-right: auto;
                }
                #keywords {
                    float: right;
                    width: 230px;
                }
                paper-input-container.iea {
                    --paper-input-container-color: red;
                    --paper-input-container-focus-color: blue;
                    --paper-input-container-invalid-color: green;
                    --paper-input-container-input-color: black;
                }
            </style>
            ${DropZoneStyles}

            <section>
                <div class="modal-header">
                    <h2>Change Category</h2>
                    <span @click="${(evt) => this.close(evt)}" class="close">&times;</span>
                </div>
                <div class="modal-body">
                    <div class="grid-container">
                        <vaadin-grid id="grid" style="box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19); grid-area: grida;" class="grid" theme="row-dividers" items="${this.categories}">
                            <vaadin-grid-column name="categories" path="name"></vaadin-grid-column>
                        </vaadin-grid>
                        <input id="categoryInput" class="form-control" style="grid-area: inputa;">
                        <vaadin-checkbox style="margin-left: auto; margin-right: auto;">Add as Subcategory</vaadin-checkbox>
                        <div class="btn-group submitBtn" style="position: relative !important; grid-area: buttona;">
                            <button type="button" class="btn btn-primary" style="background-color: #288b9e !important; min-width: 100px; margin-left: auto;" @click="${(evt) => this.addCategory(evt)}">Add</button>
                            <button type="button" class="btn btn-primary" style="background-color: #288b9e !important; min-width: 100px; margin-right: auto;" @click="${(evt) => this.changeCategory(evt)}">Save</button>
                        </div>
                        
                        <vaadin-grid id="keywords" class="grid" style="box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19); grid-area: gridb;" theme="row-dividers" items="${this.keywords}">
                            <vaadin-grid-column name="keywords" path="keyword"></vaadin-grid-column>
                            <vaadin-grid-column width="7em"></vaadin-grid-column>
                        </vaadin-grid>
                        <input id="keywordInput" class="form-control" style="grid-area: inputb; margin-top: auto; margin-bottom: auto;">
                        <div class="btn-group submitBtn" style="position: relative !important; grid-area: buttonb;">
                            <button type="button" class="btn btn-primary" style="background-color: #288b9e !important; min-width: 100px; margin-left: auto;" @click="${(evt) => this.addKeyword(evt)}">Add</button>
                            <button type="button" class="btn btn-primary" style="background-color: #288b9e !important; min-width: 100px; margin-right: auto;" @click="${(evt) => this.changeKeywords(evt)}">Save</button>
                        </div>
                  </div>
                </div>
            </section>
        `;
    }

    addCategory(evt) {
        const categoryname = this.shadowRoot.querySelector('#categoryInput').value;
        var grid = this.shadowRoot.querySelector('#grid');
        var parentid = null;
        if (this.shadowRoot.querySelector('vaadin-checkbox').checked) {
            if (! grid.selectedItems[0].name.startsWith('--> ')) {
                parentid = grid.selectedItems[0].id;
            } else {
                parentid = grid.selectedItems[0].parentCategoryId;
            }
        }
        const catview = this;
        if (parentid) {
            fetch('http://localhost:8080/iea/api/payments/addcategory/' + parentid, {
                method: 'POST',
                headers: {
                    "Content-Type": "text/plain"
                },
                body: categoryname
            }).then(response => {
                console.log(response);
                catview.shadowRoot.querySelector('vaadin-checkbox').checked = false;
                catview.shadowRoot.querySelector('#categoryInput').value = "";
                catview.loadContent("t");
            });
        } else {
            fetch('http://localhost:8080/iea/api/payments/addcategory', {
                method: 'POST',
                headers: {
                    "Content-Type": "text/plain"
                },
                body: categoryname
            }).then(response => {
                console.log(response);
                catview.shadowRoot.querySelector('vaadin-checkbox').checked = false;
                catview.shadowRoot.querySelector('#categoryInput').value = "";
                catview.loadContent("t");
            });
        }
    }

    addKeyword(evt) {
        const keyword = this.shadowRoot.querySelector('#keywordInput').value;
        const catview = this;
        const categoryid = this.shadowRoot.querySelector('#grid').selectedItems[0].id;
        fetch('http://localhost:8080/iea/api/preaccounting/assignment/addkeyword/' + categoryid, {
            method: 'POST',
            headers: {
                "Content-Type": "text/plain"
            },
            body: keyword
        }).then(response => {
            console.log(response);
            catview.shadowRoot.querySelector('#keywordInput').value = "";
            catview.loadKeywords();
        });
    }

    changeCategory(evt) {
        let ids = this.currentPayment + ';' + this.shadowRoot.querySelector('#grid').selectedItems[0].id;

        fetch('http://localhost:8080/iea/api/payments/changecategory', {
            method: 'POST',
            headers: {
                "Content-Type": "text/plain"
            },
            body: ids
        }).then(
            response => {
                console.log(response);
            }
        ).catch(
            error => console.log(error)
        );
    }

    changeKeywords(evt) {
        ///' + this.currentCategory
        fetch('http://localhost:8080/iea/api/preaccounting/assignment/' + this.currentCategory, {
            method: 'POST',
            headers: {
                "Content-Type": "text/plain"
            },
            body: this.keywords
        }).then(response => {
            console.log(response);
        });
    }

    loadContent(evt) {
        var grid = this.shadowRoot.querySelector('#grid');
        grid.addEventListener('active-item-changed', function(event) {
            const item = event.detail.value;
            if (item) {
                grid.selectedItems = [item];
            }
        });

        var catview = this;
        fetch('http://localhost:8080/iea/api/preaccounting/category')
        .then(async function(response) {
            let tmp = await response.json();
            var categories = JSON.stringify(tmp);
            catview.categories = categories;
            var columns = catview.shadowRoot.querySelector("#grid").querySelectorAll("vaadin-grid-column");
            console.log(columns);
            columns[0].headerRenderer = function(root, column) {
                root.innerHTML = '<div style="font-weight: bold">Categories</div>';
            };
        }).then(async function () {
            catview.setSelectedCategory();

            var grid = catview.shadowRoot.querySelector("#grid");
            grid.addEventListener('click', function(e) {
                const item = grid.getEventContext(e).item;
                catview.currentCategory = item.id;
                catview.loadKeywords();
            });
        });
    }

    loadKeywords() {
        var catview = this;

        fetch('http://localhost:8080/iea/api/preaccounting/assignment/' + this.currentCategory, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json"
            }
        })
        .then(async  function(response) {
            let tmp = await response.json();
            var keywords = JSON.stringify(tmp);
            catview.keywords = keywords;

            const columns = catview.shadowRoot.querySelector("#keywords").querySelectorAll('vaadin-grid-column');

            columns[0].headerRenderer = function(root, column) {
                root.innerHTML = '<div style="font-weight: bold">Keywords</div>';
            };

            columns[1].renderer = function(root, column, rowData) {
                let wrapper = root.firstElementChild;
                if (!wrapper) {
                    root.innerHTML =
                        '<div style="text-align: right">' +
                        '<vaadin-button aria-label="Delete" theme="icon error">' +
                        '<iron-icon icon="clear"></iron-icon>' +
                        '</vaadin-button>' +
                        '</div>';
                    wrapper = root.firstElementChild;

                    const button = wrapper.querySelector('vaadin-button');
                    button.addEventListener('click', function() {
                        var tmp = JSON.parse(catview.keywords);
                        tmp.splice(wrapper.idx, 1);
                        catview.keywords = JSON.stringify(tmp);
                    });
                }

                // We reuse rendered content, but maintain a property with the index for actions
                wrapper.idx = rowData.index;
            };
        });
    }

    async setSelectedCategory() {
        var grid = this.shadowRoot.querySelector('#grid');
        console.log(await grid.items);
        for (var i = 0; i < grid.items.length; i++) {
            if(grid.items[i].id == this.currentCategory) {
                grid.selectedItems = [];
                grid.selectedItems = [grid.items[i]];
                console.log(grid.selectedItems);
                break;
            }
        }
    }

    close(evt) {
        this.parentElement.offsetParent.updatePayments();
        this.style.display = "none";
    }
}

window.customElements.define('category-view', Category);