import { html } from '@polymer/lit-element';
import { PageViewElement } from './page-view-element.js';

// These are the shared styles needed by this element.
import { SharedStyles } from './shared-styles.js';

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
