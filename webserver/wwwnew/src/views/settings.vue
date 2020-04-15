<template>
  <div>
    <h2 class="content-block">Settings</h2>
    <div class="content-block">
      <div class="dx-card responsive-paddings">
      <div class="md-layout md-gutter">
          <div class="md-layout-item fullheight md-size-30">
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
          <div class="md-layout-item" style="position: relative">
            <md-chips
              v-model="keywords"
              md-placeholder="New keyword"
              @md-insert="updateKeywords()"
              @md-delete="updateKeywords()"
            ></md-chips>

            <div class="add-div">
              <md-field>
                <md-input class="add-input" placeholder="New category" v-model="newCategoryName"></md-input>
              </md-field>
              <dx-check-box
                style="line-height: 3"
                v-model="addAsSubcategory"
                text="Add as Subcategory"
              ></dx-check-box>
              <md-button class="md-raised md-primary add-btn right" @click="addCategory()">Add</md-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="js">
import Vue from "vue";
import { DxTreeView, DxScrollView, DxCheckBox, DxNumberBox, DxSelectBox } from 'devextreme-vue';

export default Vue.extend({
  data() {
    return {
      categories: new Array(),
      keywords: [],
      newCategoryName: '',
      addAsSubcategory: false,
      currentCategoryId: -1
    };
  },
  mounted() {
    this.getAllCategories();
  },
  components: {
    DxTreeView,
    DxScrollView,
    DxCheckBox,
    DxNumberBox,
    DxSelectBox
  },
  methods: {
    getAllCategories() {
      fetch('http://localhost:8080/preaccounting/category')
      .then(async function(response) {
        let tmp = await response.json();
        this.categories = tmp;

        if (this.currentCategoryId == -1) {
          this.currentCategoryId = this.categories[0].id;
        }
        this.getSelectedItem().selected = true;
        this.getAllKeywords(this.getSelectedItem().id);
      }.bind(this));
    },
    getAllKeywords() {
      fetch('http://localhost:8080/preaccounting/assignment/' + this.currentCategoryId)
      .then(async function(response) {
        let tmp = await response.json();
        this.keywords = tmp;
      }.bind(this));
    },
    selectItem(args) {
      this.currentCategoryId = args.itemData.id;
      this.getAllKeywords();
    },
    getSelectedItem() {
      var id = this.currentCategoryId;
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
    updateKeywords() {
      fetch('http://localhost:8080/preaccounting/assignment/' + this.currentCategoryId, {
        method: 'POST',
        headers: {
            "Content-Type": "text/plain"
        },
        body: JSON.stringify(this.keywords)
      })
      .then((response) => {
        console.log(response);
      }).catch(error => {
        console.error(error);
      });
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
    }
  }
});
</script>

<style lang="scss">
.right {
  float: right;
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
.fullheight {
  height: 100%;
}
.dx-treeview-item-content > span:first-child:nth-last-child(1) {
  float: left;
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
</style>