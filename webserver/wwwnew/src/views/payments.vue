<template>
  <div>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <h2 class="content-block">Payments</h2>
    <div class="content-block">
      <div class="dx-card responsive-paddings">
        <md-steppers class="md-alternative" :md-active-step.sync="active" md-linear>
          <md-step id="first" md-label="Upload CSV" :md-editable="false" :md-done.sync="first">
            <div class="content-area">
              <form
                class="drop-zone"
                id="file-form"
                action="http://localhost:8080/files/uploadcsv"
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
                <md-button
                  class="md-raised md-primary right"
                  @click="handleSubmit()"
                  :disabled="disableButtons"
                >Upload {{getFilesCount()}} File(s)</md-button>
                <md-button
                  @click="cancleUpload()"
                  class="md-raised right"
                  :disabled="disableButtons"
                >Cancel</md-button>
                <br>
              </div>
            </div>
            <md-button
              class="md-raised md-primary right"
              @click="nextStep('first', 'second'); getAllTempPayments();"
            >Next</md-button>
          </md-step>
          <md-step
            id="second"
            md-label="Check Category"
            :md-editable="false"
            :md-done.sync="second"
          >
            <md-empty-state
              md-rounded
              md-icon="report_problem"
              md-label="No uploaded payments!"
              md-description="The uploaded payments will be displayed here."
              v-if="this.payments.length == 0"
            ></md-empty-state>
            <md-table v-if="this.payments.length != 0">
              <md-table-row class="dark-head">
                <md-table-head class="white-color">Bookingdate</md-table-head>
                <md-table-head class="white-color" md-numeric>Amount</md-table-head>
                <md-table-head class="white-color">Currency</md-table-head>
                <md-table-head class="white-color">Bookingtext</md-table-head>
                <md-table-head class="white-color">Category</md-table-head>
              </md-table-row>
              <md-table-row v-for="payment in payments" :key="payment.id">
                <md-table-cell>{{payment.bookingDate}}</md-table-cell>
                <md-table-cell md-numeric>{{payment.amount}}</md-table-cell>
                <md-table-cell>{{payment.currency}}</md-table-cell>
                <md-table-cell>{{payment.bookingText}}</md-table-cell>
                <md-table-cell>
                  <md-button
                    class="categoryButton"
                    :style="{ backgroundColor: getBackgroundColor(payment.category.id) }"
                    @click="showCategoryPopup(payment)"
                  >{{payment.category.name}}</md-button>
                </md-table-cell>
              </md-table-row>
            </md-table>
            <md-button class="md-raised" @click="previousStep('second', 'first')">Back</md-button>
            <md-button class="md-raised md-primary right" @click="nextStep('second', 'third')">Next</md-button>
          </md-step>
          <md-step id="third" md-label="Write-off" :md-editable="false" :md-done.sync="third">
            <md-empty-state
              md-rounded
              md-icon="report_problem"
              md-label="No uploaded payments!"
              md-description="The uploaded payments will be displayed here."
              v-if="this.payments.length == 0"
            ></md-empty-state>
            <md-table v-if="this.payments.length != 0">
              <md-table-row class="dark-head">
                <md-table-head class="white-color">Bookingdate</md-table-head>
                <md-table-head class="white-color" md-numeric>Amount</md-table-head>
                <md-table-head class="white-color">Currency</md-table-head>
                <md-table-head class="white-color">Bookingtext</md-table-head>
                <md-table-head class="white-color">Write-off</md-table-head>
              </md-table-row>
              <md-table-row v-for="payment in payments" :key="payment.id">
                <md-table-cell>{{payment.bookingDate}}</md-table-cell>
                <md-table-cell md-numeric>{{payment.amount}}</md-table-cell>
                <md-table-cell>{{payment.currency}}</md-table-cell>
                <md-table-cell>{{payment.bookingText}}</md-table-cell>
                <md-table-cell>
                  <md-button
                    class="categoryButton"
                    :style="{ backgroundColor: payment.writeOffUnit == 0 ? '#CC0000' : '#00CC00' }"
                    @click="showWriteOffPopup(payment)"
                  >Write-off</md-button>
                </md-table-cell>
              </md-table-row>
            </md-table>
            <md-button class="md-raised" @click="previousStep('third', 'second')">Back</md-button>
            <md-button
              class="md-raised md-primary right"
              @click="commitPayments()"
              :disabled="this.payments.length == 0"
            >Commit</md-button>
          </md-step>
        </md-steppers>
        <dx-popup
          :visible.sync="categoryPopupVisible"
          :drag-enabled="false"
          :close-on-outside-click="true"
          :show-title="true"
          :width="265"
          :height="500"
          v-bind:title="changeCategoryHeader"
        >
          <div class="md-layout md-gutter fullheight">
            <div class="md-layout-item fullheight">
              <dx-scroll-view>
                <div>
                  <dx-tree-view
                    id="categoryview"
                    ref="categoryview"
                    class="categoryview"
                    :items="categories"
                    :width="200"
                    @item-click="selectItem"
                    :selectByClick="true"
                    :selectionMode="'single'"
                  />
                </div>
              </dx-scroll-view>
            </div>
          </div>
        </dx-popup>
        <dx-popup
          :visible.sync="writeoffPopupVisible"
          :drag-enabled="false"
          :close-on-outside-click="true"
          :show-title="true"
          :width="350"
          :height="250"
          v-bind:title="'Write-off'"
        >
          <div class="md-layout md-gutter fullheight" style="position: relative;">
            <div class="md-layout-item">
              <dx-select-box :items="units" :value.sync="writeoffunit"/>
            </div>
            <div class="md-layout-item fullheight">
              <dx-number-box :min="2" :show-spin-buttons="true" :value.sync="writeOffNumber"/>

              <div class="add-div" style="width: 50% !important;">
                <md-button class="md-raised md-primary add-btn right" @click="saveWriteOff()">Save</md-button>
              </div>
            </div>
          </div>
        </dx-popup>
      </div>
    </div>
  </div>
</template>

<script lang="js">
import Vue from "vue";
import { DxPopup, DxTreeView, DxScrollView, DxCheckBox, DxNumberBox, DxSelectBox } from 'devextreme-vue';

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
      payments: new Array(),
      categoryPopupVisible: false,
      writeoffPopupVisible: false,
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
      currentPayment: null,
      categories: new Array(),
      active: 'first',
      first: false,
      second: false,
      third: false,
      writeOffNumber: 2,
      writeoffunit: 'None',
      units: ['None', 'Month', 'Quarter', 'Year'],
      changeCategoryHeader: 'Change Category'
    };
  },
  components: {
    DxPopup,
    DxTreeView,
    DxScrollView,
    DxCheckBox,
    DxNumberBox,
    DxSelectBox
  },
  methods: {
    nextStep (id, index) {
      this[id] = true;

      if (index) {
        this.active = index;
      }
    },
    previousStep(id, index) {
      this[id] = false;

      if (index) {
        this[index] = false;
        this.active = index;
      }
    },
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

            fetch('http://localhost:8080/files/uploadtext', {
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
      fetch('http://localhost:8080/preaccounting/commit', {
        method: "POST"
      }).then(function(response) {
        this.getAllTempPayments();
      }.bind(this));
    },
    getBackgroundColor(catid) {
      return this.colors[catid];
    },
    showCategoryPopup(payment) {
      this.categoryPopupVisible = true;
      this.currentPayment = payment;
      this.getAllCategories();
    },
    showWriteOffPopup(payment) {
      this.writeoffPopupVisible = true;
      this.currentPayment = payment;
      if (this.currentPayment.writeOffNumber >= 2){
        this.writeOffNumber = this.currentPayment.writeOffNumber;
      }else {
        this.writeOffNumber = 2;
      }
      var unit = 'None';
      switch (this.currentPayment.writeOffUnit) {
        case 1:
          unit = "Month";
          break;
        case 2:
          unit = "Quarter";
          break;
        case 3:
          unit = "Year";
          break;
        default:
          break;
      }
      this.writeoffunit = unit;
    },
    getAllPayments() {
      fetch('http://localhost:8080/payments')
      .then(async function(response) {
        let tmp = await response.json();
        this.payments = tmp;
      }.bind(this));
    },
    getAllTempPayments() {
      fetch('http://localhost:8080/payments/temp')
      .then(async function(response) {
        let tmp = await response.json();
        console.log(response);
        this.payments = tmp;
        console.log(this.payments);
      }.bind(this));
    },
    getAllCategories() {
      fetch('http://localhost:8080/preaccounting/category')
      .then(async function(response) {
        let tmp = await response.json();
        this.categories = tmp;

        this.getSelectedItem().selected = true;
      }.bind(this));
    },
    selectItem(args) {
      if (this.currentPayment.category.id != args.itemData.id) {
        this.currentPayment.category.id = args.itemData.id;
        this.currentPayment.category.name = args.itemData.text;

        this.saveCategory();
      }
    },
    getSelectedItem() {
      var id = this.currentPayment.category.id;
      var returnval = null;
      this.categories.forEach(item => {
        if (item.id == id) {
          returnval = item;
        } else {
          if (item.items) {
            item.items.forEach(i => {
              if (i.id == id) {
                returnval = i;
              }
            });
          }
        }
      });
      return returnval;
    },
    saveCategory() {
      fetch('http://localhost:8080/payments/changecategory/' + this.currentPayment.id, {
        method: 'POST',
        body: JSON.stringify({catId: this.currentPayment.category.id.toString() }), 
        headers: {'Content-Type': 'text/plain'}
      })
      .then(function(response) {
      }.bind(this)).catch(error => alert(error));
    },
    addCategory() {
      var url = '';
      if (this.addAsSubcategory) {
        var selectedItem = this.getSelectedItem();
        if (typeof selectedItem !== 'undefined' && selectedItem) {
          var parentId = this.getSelectedItem().parentId == -1 ? this.getSelectedItem().id : this.getSelectedItem().parentId;
          url = 'http://localhost:8080/payments/addcategory/' + parentId;
        }
      } else {
        url = 'http://localhost:8080/payments/addcategory';
      }

      if (url != '') {
        fetch(url, {
          method: 'POST',
          headers: {
              "Content-Type": "text/plain"
          },
          body: this.newCategoryName
        }).then(function(response) {
          console.log(response);
          this.addAsSubcategory = false;
          this.newCategoryName = "";
          this.getAllCategories();
        }.bind(this));
      }
    },
    saveWriteOff() {
      if (this.writeOffNumber != 'None') {
        var writeoff = {
          wunit: this.writeoffunit,
          wnum: this.writeOffNumber
        };

        fetch('http://localhost:8080/preaccounting/writeoff/' + this.currentPayment.id, {
          method: 'POST',
          headers: {
              "Content-Type": "text/plain"
          },
          body: JSON.stringify(writeoff)
        }).then(function(response) {
          this.getAllPayments();
          this.writeoffPopupVisible = false;
          console.log(response);
        }.bind(this));
      }
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
  min-width: 135px !important;
}
.gridcontainer {
  padding: 0px !important;
}
.fullheight {
  height: 100%;
}
.dx-treeview-item-content > span:first-child:nth-last-child(1) {
  float: left;
}
.dark-head {
  background-color: #363640;
}
.white-color {
  color: white !important;
}
.add-btn {
  margin-left: 15px;
}
.add-div {
  margin-top: 25px;
  width: 100%;
  position: absolute;
  bottom: 1px;
  padding-right: 25px;
}
.add-input {
  width: 100%;
}
.md-field {
  margin: 0 !important;
}
</style>