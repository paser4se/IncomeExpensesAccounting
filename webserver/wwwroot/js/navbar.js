import {LitElement, html} from "@polymer/lit-element";

class NavBar extends LitElement {

    constructor() {
        super();
    }

    render() {
        return html`
        <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.5.16/css/mdb.min.css" rel="stylesheet">
        
        <nav class="mb-1 navbar sticky-top navbar-expand-lg navbar-dark info-color" style="background-color: #288B9E !important;">
            <a class="navbar-brand" style="font-weight: bold !important; color: #FFFFFF;">
                <img src="../Logo.png" height="30" class="d-inline-block align-top" alt="IEA Logo"> IEA
            </a>
            
            <button class="navbar-toggler" type="button" data-toggle="collapse">
                <span class="navbar-toggler-icon"></span>
            </button>
            
            <div class="collapse navbar-collapse" id="navbarSupportedContent-4">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item active" id="nav_home">
                        <a class="nav-link waves-effect waves-light" @click="${evt => this.handleActive(evt)}">
                            <i class="fa fa-home" aria-hidden="true"></i> Home
                        </a>
                    </li>
                    <li class="nav-item" id="nav_payments">
                        <a class="nav-link waves-effect waves-light" @click="${evt => this.handleActive(evt)}">
                            <i class="fa fa-credit-card" aria-hidden="true"></i> Payments
                        </a>
                    </li>
                    <li class="nav-item" id="nav_overview">
                        <a class="nav-link waves-effect waves-light" @click="${evt => this.handleActive(evt)}">
                            <i class="fa fa-tablet" aria-hidden="true"></i> Overview
                        </a>
                    </li>
                    <li class="nav-item" id="nav_evaluation">
                        <a class="nav-link waves-effect waves-light" @click="${evt => this.handleActive(evt)}">
                            <i class="fa fa-bar-chart" aria-hidden="true"></i> Evaluation
                        </a>
                    </li>
                    <li class="nav-item dropdown" id="nav_dropdown">
                        <a class="nav-link dropdown-toggle waves-effect waves-light" id="navbarDropdownMenuLink-4" @click="${evt => {this.handleDropDown(evt); this.handleActive(evt)}}" data-toggle="dropdown">
                          <i class="fa fa-user"></i> Profil</a>
                        <div class="dropdown-menu dropdown-menu-right dropdown-info">
                          <a class="dropdown-item waves-effect waves-light" href="#">My account</a>
                          <a class="dropdown-item waves-effect waves-light" href="#">Log out</a>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>
        `;
    }

    handleActive(evt) {
        var active = this.shadowRoot.getElementsByClassName('active')[0];
        var parent = evt.srcElement.parentElement;
        active.classList.remove('active');
        if (! evt.srcElement.classList.contains('dropdown-toggle')) {
            var dropdownmenu = this.shadowRoot.getElementsByClassName('dropdown-menu')[0];
            var dropdown = this.shadowRoot.getElementsByClassName('dropdown')[0];
            dropdownmenu.classList.remove("show");
            dropdown.classList.remove("show");
        }

        if (parent.classList.contains('nav-link')) {
            parent = parent.parentElement;
        }
        parent.classList.add('active');

        document.querySelector("content-view").changeInner(parent.id);

        /*const event = new CustomEvent('navbar', {
            detail: {
                uri: parent.getElementsByClassName('nav-link')[0].innerText.trim()
            }
        });

        this.dispatchEvent(event);*/
    }

    handleDropDown(evt) {
        var dropdownmenu = this.shadowRoot.getElementsByClassName('dropdown-menu')[0];
        var dropdown = this.shadowRoot.getElementsByClassName('dropdown')[0];
        if (! dropdownmenu.classList.contains("show")) {
            dropdownmenu.classList.add("show");
        } else {
            dropdownmenu.classList.remove("show");
        }
        if (! dropdown.classList.contains("show")) {
            dropdown.classList.add("show");
        } else {
            dropdown.classList.remove("show");
        }
    }
}

customElements.define('nav-bar', NavBar);