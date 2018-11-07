import { LitElement, html } from "./node_modules/@polymer/lit-element/lit-element.js";

class DropZone extends LitElement {
  constructor() {
    super();
    this.droppedfiles = [];
  }

  static get properties() {
    return {
      droppedfiles: {
        type: Array
      }
    };
  }

  render() {
    return html`
            <style>
                .drop-zone
                {
                    background-color: rgb(200, 218, 223);
                    opacity: 0.9;
                    outline: 2px dashed rgb(15, 60, 75);
                    outline-offset: -6px;
                    margin-top: 20px;
                    margin-left: auto;
                    margin-right: auto;
                    padding: 10px;
                    width: 50%;
                    height: 40%;
                    text-align: center;
                    position: relative;
                }
                .drop-content
                {
                    position: absolute;
                    top: 50%;
                    left: 50%;
                    transform: translate(-50%, -50%);
                }
                .drop-zone.entered
                {
                    background-color: rgb(231, 241, 243);
                    outline: 3px dashed rgb(15, 60, 75);
                }
                label
                {
                    color: #0f3c4b;
                    font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
                    font-size: 24px;
                }
                label:hover, button:hover
                {
                    color: #39bfd3;
                }
                span
                {
                    color: #0f3c4b !important;
                }
                button
                {
                    font-weight: 700;
                    color: white !important;
                    background-color: #39bfd3;
                    display: block;
                    padding: 8px 16px;
                    margin: 40px auto 0;
                    border-radius: 5px;
                    border: none;
                }
                button:hover
                {
                    background-color: #0f3c4b;
                }
            </style>
            <form class="drop-zone" id="file-form" action="http://localhost:8085/iea/rs/files/uploadcsv" method="POST" enctype="multipart/form-data"
                    @dragenter="${evt => this.handleDragenter(evt)}" @dragstart="${evt => this.handleDragstart(evt)}"
                    @dragleave="${evt => this.handleDragleave(evt)}" @drop="${evt => this.handleDrop(evt)}"
                    @dragover="${evt => this.handleDragover(evt)}">
                <div class="drop-content">
                    <img id="dropdownimg" src="https://cdn2.iconfinder.com/data/icons/freecns-cumulus/16/519672-178_Download-512.png"
                    style="height: 15px; width: 15px;">
                    <input type="file" id="file-select" name="csv[]" accept=".csv" multiple style="display: none;" 
                            @submit="${evt => this.handleSubmit(evt)}"/>
                    <label for="file-select" id="choosefilelbl"><strong>Choose a file</strong><span> or drop it here...</span></label><br>
                    <label id="filename">0 file(s) selected</label>
                    <button type="submit" id="upload-button">Upload</button>
                </div>
            </form>
        `;
  }

  handleDragenter(evt) {
    evt.originalTarget.classList.add('entered');
  }

  handleDragstart(evt) {
    evt.dataTransfer.setData('text/html', null);
  }

  handleDragover(evt) {
    evt.preventDefault();
  }

  handleDragleave(evt) {
    evt.originalTarget.classList.remove('entered');
  }

  handleDrop(evt) {
    evt.preventDefault();
    var file = evt.dataTransfer.files[0];
    evt.originalTarget.classList.remove('entered');
    console.log(file);
    this.droppedfiles.push(file);
    evt.originalTarget.querySelectorAll("label")[1].innerHTML = this.deleteDuplicates(evt.originalTarget.querySelector(".file-select")).length + " file(s) selected";
  }

  handleSubmit(evt) {
    evt.preventDefault();
    evt.originalTarget.querySelector("button").innerHTML = 'Uploading...';
    var files = deleteDuplicates(evt.originalTarget.querySelector(".file-select"));
    var formData = new FormData();

    for (var i = 0; i < files.length; i++) {
      formData.append('csv[]', files[i], files[i].name);
    }

    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8085/iea/rs/files/uploadcsv', true);

    xhr.onload = function () {
      if (xhr.status === 200) {
        // File(s) uploaded.
        evt.originalTarget.querySelector("button").innerHTML = 'Upload';
      } else {
        alert('An error occurred!');
      }
    };

    xhr.send(formData);
  }

  deleteDuplicates(fileSelect) {
    var selectedFiles = [];

    for (var i = 0; i < this.droppedfiles.length; i++) {
      if (!this.contains(selectedFiles, this.droppedfiles[i])) {
        selectedFiles.push(this.droppedfiles[i]);
      }
    }

    if (fileSelect) {
      for (var a = 0; a < fileSelect.files.length; a++) {
        if (!this.contains(selectedFiles, fileSelect.files[a])) {
          selectedFiles.push(fileSelect.files[a]);
        }
      }
    }

    return selectedFiles;
  }

  contains(arr, file) {
    for (var i = 0; i < arr.length; i++) {
      if (arr[i].name === file.name) {
        return true;
      }
    }

    return false;
  }

}

customElements.define('drop-zone', DropZone);
/*

postData(name) {
    console.log(name);

    // https://stackoverflow.com/a/51854096/9818338
    const userAction = async() => {
      const response = await fetch('http://localhost:8080/simple/rs/data', {
        method: 'POST',
        body: JSON.stringify({name: name}),
        headers:{
          'Content-Type': 'application/json'
        }
      });
      const myJson = await response.json();
    }
    userAction();


    // h<ttps://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch
    // fetch('http://localhost:8080/simple/rs/data', {
    //   method: 'POST',
    //   mode: 'cors',
    //   cache: 'no-cache',
    //   credentials: 'same-origin',
    //   body: {name: name},
    //   headers: {
    //     'Content-Type': 'application/json'
    //   },
    //   redirect: "follow",
    //   referrer: "no-referrer"
    // })
    //   .then(response => response.json())
    //   .then(res => console.log(JSON.stringify(res)));

  }

*/