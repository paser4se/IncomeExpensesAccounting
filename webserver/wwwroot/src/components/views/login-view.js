import { html } from '@polymer/lit-element';
import { PageViewElement } from '../page-view-element.js';

// These are the shared styles needed by this element.
import { SharedStyles } from '../shared-styles.js';

class LoginView extends PageViewElement {
    render() {
      return html`
        ${SharedStyles}
        <section>
          <div>Login</div>
        </section>
      `;
    }
}

window.customElements.define('login-view', LoginView);
