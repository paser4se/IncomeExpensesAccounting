import { html } from '@polymer/lit-element';

export const AppStyle = html`
<style>
  :host {
    --app-drawer-width: 256px;
    display: block;

    --app-primary-color: #288b9e;
    --app-secondary-color: rgb(41, 50, 55);
    --app-dark-text-color: var(--app-secondary-color);
    --app-light-text-color: white;
    --app-section-even-color: #f7f7f7;
    --app-section-odd-color: white;

    --app-header-background-color: white;
    --app-header-text-color: var(--app-dark-text-color);
    --app-header-selected-color: var(--app-primary-color);

    --app-drawer-background-color: var(--app-secondary-color);
    --app-drawer-text-color: var(--app-light-text-color);
    --app-drawer-selected-color: #288b9e;
  }

  app-header {
    position: fixed;
    width: 100%;
    text-align: center;
    background-color: var(--app-header-background-color);
    color: var(--app-header-text-color);
    border-bottom: 1px solid #eee;
    z-index: 1;
  }

  .toolbar-top {
    background-color: var(--app-header-background-color);
  }

  [main-title] {
    color: var(--app-primary-color);
    padding-top: 20px;
    font-family: 'Dyuthi';
    font-weight: bold;
    font-size: 75px;
    /* In the narrow layout, the toolbar is offset by the width of the
    drawer button, and the text looks not centered. Add a padding to
    match that button */
    padding-right: 44px;
  }
  
  [left-item] {
    float: left !important;
  }
  
  [right-item] {
    float: right !important;
  }

  .toolbar-list {
    display: none;
    text-align: left !important;
  }

  .toolbar-list > a {
    display: inline-block;
    color: var(--app-header-text-color);
    text-decoration: none;
    line-height: 30px;
    padding: 4px 24px;
    outline: 0;
  }

  .toolbar-list > a[selected] {
    /*color: var(--app-header-selected-color);*/
    border-bottom: 4px solid var(--app-header-selected-color);
    
    outline: 0;
  }

  .menu-btn {
    background: none;
    border: none;
    fill: var(--app-header-text-color);
    cursor: pointer;
    height: 44px;
    width: 44px;
  }

  .drawer-list {
    box-sizing: border-box;
    width: 100%;
    height: 100%;
    padding: 24px;
    background: var(--app-drawer-background-color);
    position: relative;
  }

  .drawer-list > a {
    display: block;
    text-decoration: none;
    color: white !important;
    line-height: 40px;
    padding: 0 24px;
    outline: 0;
  }
  .drawer-list > div {
    color: white !important;
  }

  .drawer-list > a[selected] {
    color: var(--app-drawer-selected-color);
    outline: 0;
  }

  /* Workaround for IE11 displaying <main> as inline */
  main {
    display: block;
  }

  .main-content {
    padding-top: 64px;
    min-height: calc(100vh - 260px);
  }

  .page {
    display: none;
  }

  .page[active] {
    display: block;
    position: relative;
    height: calc(100vh - 260px);
  }

  footer {
    margin-bottom: -2px;
    padding: 24px;
    background: var(--app-drawer-background-color);
    color: var(--app-drawer-text-color);
    text-align: center;
  }
  
  nav.pullUp a:before, nav.pullUp div.profile:before
  {
      position: absolute;
      width: 0px;
      height: 0px;
      left: 0px;
      bottom: 0px;
      content: '';
      background: var(--app-header-selected-color);
      opacity: 0.3;
      transition: all 0.3s;
  }
  
  nav.pullUp a:hover:before, nav.pullUp div.profile:hover:before
  {
      width: 100%;
      height: 2px;
  }
  
  nav div.profile:hover div.dropdown-content {
    display: block;
  }
  
  nav.container
  {
      /*font-family: Raleway;*/
      margin: 0 auto;
      /*padding: 10em 3em;*/
      text-align: center;
  }
  
  .profile {
    position: relative;
  }
  
  nav.container a, nav.container div
  {
      color: var(--app-header-text-color);
      text-decoration: none;
      font: 20px Ubuntu;
      margin: 0px 10px;
      padding: 10px 10px;
      position: relative;
      z-index: 0;
      cursor: pointer;
  }
  
  .dropdown-content {
    display: none;
    position: absolute !important;
    background-color: #f9f9f9;
    margin-top: 25px;
    min-width: 160px;
    right: 0;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
  }
  
  .dropdown-content a, .dropdown-content div {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
  }

  /* Wide layout: when the viewport width is bigger than 460px, layout
  changes to a wide layout. */
  @media (min-width: 460px) {
    .toolbar-list {
      display: block;
    }

    .menu-btn {
      display: none;
    }

    .main-content {
      padding-top: 107px;
      min-height: calc(100vh - 212px);
    }

    /* The drawer button isn't shown in the wide layout, so we don't
    need to offset the title */
    [main-title] {
      padding-right: 0px;
    }
  }
</style>
`;