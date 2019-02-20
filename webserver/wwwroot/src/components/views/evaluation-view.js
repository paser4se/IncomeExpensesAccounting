import { html } from '@polymer/lit-element';
import { PageViewElement } from '../page-view-element.js';

import {SharedStyles} from "../shared-styles";

class EvaluationView extends PageViewElement {

    render() {
        return html`
            ${SharedStyles}

            <section>
                <div>Evaluation</div>
            </section>
        `;
    }
}

window.customElements.define('evaluation-view', EvaluationView);
