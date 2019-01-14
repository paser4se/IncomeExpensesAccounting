import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon';
import { SharedStyles } from './shared-styles.js';


class MySong extends LitElement {
    render() {
        return html`
            ${SharedStyles}
            <div class="ui grid">
                <div class="four wide column">
                    <img src="/images/covers/dreamer_sunriseavenue.jpg" class="ui small center aligned image" style="margin: 0 auto; ">
                </div>
                <div class="eight wide column" style="margin: auto 0">
                    <h3 class="ui header" style="margin-bottom: 0">title</h3>
                    <p style="margin: 0 0 15px 0">Interpret</p>
                </div>
                <div class="four wide column" style="margin: auto auto;">
                    <img src="/images/heartGrey.png" class="ui avatar image" style="margin: auto 0;">
                </div>
            </div>    
            <div class="ui divider">  </div>
            `;
    }
}

window.customElements.define('my-song', MySong);