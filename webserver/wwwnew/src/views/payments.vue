<template>
  <div>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <h2 class="content-block">Payments</h2>
    <div class="content-block">
      <div class="dx-card responsive-paddings">
        <v-app>
          <v-stepper v-model="e1">
            <v-stepper-header>
              <v-stepper-step :complete="e1 > 1" step="1">Upload CSV</v-stepper-step>
              <v-divider></v-divider>
              <v-stepper-step :complete="e1 > 2" step="2">Check Category</v-stepper-step>
              <v-divider></v-divider>
              <v-stepper-step step="3">Write-off</v-stepper-step>
            </v-stepper-header>

            <v-stepper-items>
              <v-stepper-content step="1">
                <div class="content-area">
                  <form
                    class="drop-zone"
                    id="file-form"
                    action="http://localhost:8085/iea/rs/files/uploadcsv"
                    method="POST"
                    enctype="multipart/form-data"
                    v-on:dragenter="handleDragenter"
                    v-on:dragstart="handleDragstart"
                    v-on:dragleave="handleDragleave"
                    v-on:drop="handleDrop"
                    v-on:dragover="handleDragover"
                  >
                    <div class="drop-content">
                      <img
                        id="dropdownimg"
                        src="https://cdn2.iconfinder.com/data/icons/freecns-cumulus/16/519672-178_Download-512.png"
                        style="height: 15px; width: 15px;"
                      >
                      <input
                        type="file"
                        v-on:change="this.handleChange"
                        id="file-select"
                        name="csv[]"
                        accept=".csv"
                        multiple
                        style="display: none;"
                      >
                      <label for="file-select" id="choosefilelbl">
                        <strong>Choose a file</strong>
                        <span style="color: #0f3c4b !important;">&nbsp;or drop it here...</span>
                      </label>
                      <br>
                      <label id="filename">{{getFilesCount()}} file(s) selected</label>
                    </div>
                  </form>
                  <div class="dropzone-btn">
                    <v-btn
                      color="primary"
                      @click="handleSubmit()"
                      class="right"
                      :disabled="disableButtons"
                    >Upload {{getFilesCount()}} File(s)</v-btn>
                    <v-btn @click="cancleUpload()" class="right" :disabled="disableButtons">Back</v-btn>
                    <br>
                  </div>
                </div>
                <v-btn @click="e1 = 1" :disabled="true">Back</v-btn>
                <v-btn color="primary" class="right" @click="e1 = 2; getAllPayments();">Next</v-btn>
              </v-stepper-content>
              <v-stepper-content step="2">
                <v-data-table
                  :headers="headers"
                  :items="payments"
                  class="elevation-1 table"
                  :hide-actions="true"
                >
                  <template v-slot:items="payments">
                    <td class="text-xs-center">{{ payments.item.bookingDate }}</td>
                    <td class="text-xs-right">{{ payments.item.amount }}</td>
                    <td class="text-xs-left">{{ payments.item.currency }}</td>
                    <td class="text-xs-center">{{ payments.item.bookingText }}</td>
                    <td class="text-xs-center">
                      <v-btn
                        depressed
                        class="categoryButton"
                        :style="{ backgroundColor: getBackgroundColor(payments.item.category.id) }"
                        @click="showPopup(payments.item)"
                      >{{payments.item.category.name}}</v-btn>
                    </td>
                  </template>
                </v-data-table>
                <v-btn @click="e1 = 1">Back</v-btn>
                <v-btn color="primary" class="right" @click="e1 = 3">Next</v-btn>
              </v-stepper-content>
              <v-stepper-content step="3">
                <v-card class="mb-5" color="grey lighten-1" style="margin-bottom: 15px;"></v-card>
                <v-btn @click="e1 = 2">Back</v-btn>
                <v-btn color="primary" class="right" @click="commitPayments()">Commit</v-btn>
              </v-stepper-content>
            </v-stepper-items>
          </v-stepper>
        </v-app>
        <dx-popup
          :visible.sync="popupVisible"
          :drag-enabled="false"
          :close-on-outside-click="true"
          :show-title="true"
          :width="450"
          :height="500"
          v-bind:title="getCurrentCategoryName()"
        >test</dx-popup>
      </div>
    </div>
  </div>
</template>

<script lang="js">
import Vue from "vue";
import { DxPopup } from 'devextreme-vue/popup';

export default Vue.extend({
  data() {
    return {
      e1: 0,
      disableDropzone: false,
      disableButtons: true,
      file: null,
      dropzoneOptions: {
        url: 'https://httpbin.org/post',
        thumbnailWidth: 150,
        autoProcessQueue: false
      },
      headers: [
        { text: 'Bookingdate', value: 'bookingdate', align: 'center', sortable: false },
        { text: 'Amount', value: 'amount', align: 'right', sortable: false },
        { text: 'Currency', value: 'currency', align: 'left', sortable: false },
        { text: 'Bookingtext', value: 'bookingtext', align: 'center', sortable: false },
        { text: 'Category', value: 'category', align: 'center', sortable: false },
      ],
      payments: new Array(),
      popupVisible: false,
      colors: [
        "#61BC6D",
        "#F57835",
        "#1BBC9B",
        "#54ACD2",
        "#75706C",
        "#EC6B58",
        "#2B82C9",
        "#9265B8",
        "#28314E",
        "#475677",
        "#41A85F",
        "#00A885",
        "#3D8EB9",
        "#2969B0",
        "#553983",
        "#F7DA64",
        "#FAA026",
        "#E14A39",
        "#A38F84",
        "#EFEFEF",
        "#FBC51D",
        "#D14840",
        "#B8312E",
        "#D1D5D8"
      ],
      currentPayment: null
    };
  },
  components: {
    DxPopup
  },
  methods: {
    getFilesCount() {
      return this.file == null ? "0" : "1";
    },
    cancleUpload() {
      this.disableDropzone = false;
      this.disableButtons = true;
      this.file = null;
    },
    handleDragenter(evt) {
        evt.originalTarget.classList.add('entered');
    },
    handleDragstart(evt) {
        evt.dataTransfer.setData('text/html', null);
    },
    handleDragover(evt) {
        evt.preventDefault();
    },
    handleDragleave(evt) {
        evt.originalTarget.classList.remove('entered');
    },
    handleChange(evt) {
      if (evt.originalTarget.files[0]) {
        this.file = evt.originalTarget.files[0];
        this.disableButtons = false;
      }
    },
    handleDrop(evt) {
        evt.preventDefault();
        var file = evt.dataTransfer.files[0];
        this.file = file;
        this.disableButtons = false;
        evt.originalTarget.classList.remove('entered');
    },
    handleSubmit() {
        this.disableButtons = true;
        const fileToLoad = this.file;
        var that = this;

        const fileReader = new FileReader();
        fileReader.onload = async function (fileLoadedEvent) {
            let textFromFileLoaded = fileLoadedEvent.target.result;

            fetch('http://localhost:8085/iea/api/files/uploadtext', {
                method: 'POST',
                headers: {
                    "Content-Type": "text/plain"
                },
                body: textFromFileLoaded
            }).then(
                response => {
                  that.file = null;
                  that.disableButtons = true;
                  console.log(response);
                }
            ).catch(
                error => {
                  console.log(error);
                  that.file = null;
                }
            )
        };

        fileReader.readAsText(fileToLoad, "UTF-8");
    },
    commitPayments() {
      //TODO
    },
    getAllPayments() {
      fetch('http://localhost:8085/iea/api/payments')
      .then(async function(response) {
        let tmp = await response.json();
        this.payments = tmp;
        console.log(this.payments);
      }.bind(this));
    },
    getBackgroundColor(catid) {
      return this.colors[catid];
    },
    showPopup(payment) {
      this.popupVisible = true;
      this.currentPayment = payment;
    },
    getCurrentCategoryName() {
      return this.currentPayment ? "Change Category (" + this.currentPayment.category.name + ")" : "";
    }
  }
});
</script>

<style lang="scss">
.content-area {
  margin-top: 15px;
  margin-bottom: 30px;
}
.dropzone-btn {
  margin-top: 15px;
  margin-bottom: 15px;
}
.right {
  float: right;
}
.drop-zone {
  background-color: #f9f9f9;
  opacity: 0.9;
  outline: 2px dashed rgb(15, 60, 75);
  outline-offset: -6px;
  margin-left: auto;
  margin-right: auto;
  margin-top: 20px;
  padding: 10px;
  width: 80%;
  height: 250px;
  text-align: center;
  position: relative;
  display: table;
}
.drop-content {
  display: table-cell;
  vertical-align: middle;
}
.drop-zone.entered {
  background-color: rgb(231, 241, 243);
  outline: 3px dashed rgb(15, 60, 75);
}
label {
  color: #0f3c4b;
  font-family: "Franklin Gothic Medium", "Arial Narrow", Arial, sans-serif;
  font-size: 24px;
}
label:hover,
button:hover {
  color: #39bfd3;
}
.application--wrap {
  min-height: auto !important;
}
.table {
  margin-bottom: 15px;
}
.categoryButton {
  color: white !important;
  min-width: 110px !important;
}
</style>