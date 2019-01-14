import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon';
import { SharedStyles } from './shared-styles.js';


class MySearch extends LitElement {
    render() {
        return html`
            ${SharedStyles}
            <div class="ui  search">
              <div class="ui icon fluid input">
                <input class="prompt" type="text" placeholder="Suche unter den Lokalen MP3s...">
                <i class="search icon"></i>
              </div>
              <div class="results"></div>
            </div> `;
         /*   <button class="ui icon button">
                    <img src="/images/youTubeGrey.png" style="max-width: 60px; max-height: 43px">
                </button>
            `;*/
    }
}

window.customElements.define('my-search', MySearch);