import {LitElement, html} from "@polymer/lit-element";

class DropZone extends LitElement {

    constructor() {
        super();
        this.droppedfiles = [];
    }

    static get properties() {
        return {
            droppedfiles: { type: Array }
        };
    }

    render() {
        return html`
            <style>
                @import "../style/dropzone.css";
            </style>
            
            <form class="drop-zone" id="file-form" action="http://localhost:8085/iea/rs/files/uploadcsv" method="POST" enctype="multipart/form-data"
                    @dragenter="${evt => this.handleDragenter(evt)}" @dragstart="${evt => this.handleDragstart(evt)}"
                    @dragleave="${evt => this.handleDragleave(evt)}" @drop="${evt => this.handleDrop(evt)}"
                    @dragover="${evt => this.handleDragover(evt)}">
                <div class="drop-content">
                    <img id="dropdownimg" src="https://cdn2.iconfinder.com/data/icons/freecns-cumulus/16/519672-178_Download-512.png"
                    style="height: 15px; width: 15px;">
                    <input type="file" @change="${evt => this.handleChange(evt)}" id="file-select" name="csv[]" accept=".csv" multiple style="display: none;" />
                    <label for="file-select" id="choosefilelbl"><strong>Choose a file</strong><span> or drop it here...</span></label><br>
                    <label id="filename">0 file(s) selected</label>
                    <button @click="${evt => this.handleSubmit(evt)}" id="upload-button">Upload</button>
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

    handleChange(evt) {
        evt.originalTarget.parentElement.querySelectorAll("label")[1].innerHTML = this.deleteDuplicates(evt.originalTarget).length + " file(s) selected";
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

        evt.srcElement.innerHTML = 'Uploading...';
        var form = evt.srcElement.form;

        const fileToLoad = this.deleteDuplicates(form.children[0].querySelector("input"))[0];

        const fileReader = new FileReader();
        fileReader.onload = async function (fileLoadedEvent) {
            let textFromFileLoaded = fileLoadedEvent.target.result;

            fetch('http://localhost:8085/iea/rs/files/uploadtext', {
                method: 'POST',
                headers: {
                    "Content-Type": "text/plain"
                },
                body: textFromFileLoaded
            }).then(
                response => {
                    //evt.originalTarget.querySelector(".fileOutput").innerHTML += textFromFileLoaded;
                    //<br><br>
                    //<div id="fileOutput" />
                    console.log(response);
                    form.children[0].querySelector("button").innerHTML = 'Upload';
                    form.children[0].querySelector("input").value = "";
                    form.children[0].querySelectorAll("label")[1].innerHTML = "0 file(s) selected";
                }
            ).catch(
                error => console.log(error)
            )
        };

        fileReader.readAsText(fileToLoad, "UTF-8");
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