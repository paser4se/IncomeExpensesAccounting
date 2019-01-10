import {LitElement, html} from "@polymer/lit-element";
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon';
import './my-song.js'
import { SharedStyles } from './shared-styles.js';


class MySongCollection extends LitElement {
    render() {
        return html`
            ${SharedStyles}
            <div class="ui divider"></div>
            <div>
                <my-song></my-song>
                <my-song></my-song>
                <my-song></my-song>
                <my-song></my-song>
                <my-song></my-song>
                <my-song></my-song>
                <my-song></my-song>
                <my-song></my-song>
                <my-song></my-song>
            </div>
            `;
    }
}

window.customElements.define('my-songcollection', MySongCollection);