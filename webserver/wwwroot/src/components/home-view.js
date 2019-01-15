import { html } from '@polymer/lit-element';
import { PageViewElement } from './page-view-element.js';
import './my-search.js';
import './my-songpreview.js'
import './my-songcollection.js'


// These are the shared styles needed by this element.
import { SharedStyles } from './shared-styles.js';

class HomeView extends PageViewElement {
  render() {
    return html`
      ${SharedStyles}
      <section>
        <div>Home</div>
      </section>
    `;
  }
}

window.customElements.define('home-view', HomeView);
