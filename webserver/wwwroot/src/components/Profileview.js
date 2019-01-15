import { html } from '@polymer/lit-element';
import { PageViewElement } from './page-view-element.js';

// These are the shared styles needed by this element.
import { SharedStyles } from './shared-styles.js';

class ProfileView extends PageViewElement {
    render() {
        return html`
      ${SharedStyles}
      <section>
        <div>Profile</div>
      </section>
    `;
    }
}

window.customElements.define('profile-view', ProfileView);
