import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon';
import { SharedStyles } from './shared-styles.js';


class MySongPreview extends LitElement {
    render() {
        return html`
            ${SharedStyles}
            <div class="ui grid">
                <div class="seven wide column">
                    <img src="/images/covers/dreamer_sunriseavenue.jpg" class="ui small center aligned image" style="margin: 0 auto">
                </div>
                <div class="nine wide column" style="margin: auto 0">
                    <h3 style="margin-bottom: 0">title</h3>
                    <h4 style="margin: 0 0 15px 0">Interpret</h4>
                    <div class="ui progress" style="border-radius: 2em; max-height: 2vh">
                      <div class="bar" style="border-radius: 2em; max-height: 2vh">
                        <div class="progress"></div>
                      </div>
                      <div class="label" style="text-align: left; font-weight: lighter; font-family: arial, sans-serif">1:23</div>
                    </div>
                </div>
            </div>
            `;
    }
}

window.customElements.define('my-songpreview', MySongPreview);