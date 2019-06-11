<template>
  <div>
    <h2 class="content-block">Evaluation</h2>
    <div class="content-block">
      <div class="dx-card responsive-paddings">
        <div class="md-layout md-gutter">
          <div class="md-layout-item full">
            <md-button class="md-raised md-primary right" @click="showFilterPayments()">Filter</md-button>
          </div>
          <div class="md-layout-item half">
            <dx-pie-chart
              id="income_pie"
              :data-source="paymentsIncome"
              palette="Bright"
              title="Income"
              @point-click="pointClickHandler($event)"
              @legend-click="legendClickHandler($event)"
            >
              <dx-series argument-field="name" value-field="amount">
                <dx-label :visible="true">
                  <dx-connector :visible="true" :width="1"/>
                </dx-label>
              </dx-series>
              <dx-size :width="500"/>
            </dx-pie-chart>
          </div>
          <div class="md-layout-item half">
            <dx-pie-chart
              id="expanses_pie"
              :data-source="paymentsExpanses"
              palette="Bright"
              title="Expenses"
              :resolveLabelOverlapping="'shift'"
              @point-click="pointClickHandler($event)"
              @legend-click="legendClickHandler($event)"
            >
              <dx-series argument-field="name" value-field="amount">
                <dx-label :visible="true">
                  <dx-connector :visible="true" :width="1"/>
                </dx-label>
              </dx-series>
              <dx-size :width="500"/>
            </dx-pie-chart>
          </div>
        </div>
        <dx-popup
          :visible.sync="filterPopupVisible"
          :drag-enabled="false"
          :close-on-outside-click="true"
          :show-title="true"
          :width="350"
          :height="250"
          v-bind:title="'Filter payments'"
        >
          Filter elements.
          <md-button class="md-raised md-primary right" @click="filterPaymentes()">Apply</md-button>
        </dx-popup>
      </div>
    </div>
  </div>
</template>

<script>
import {
  DxPieChart,
  DxSize,
  DxSeries,
  DxLabel,
  DxConnector
} from "devextreme-vue/pie-chart";
import { DxPopup } from "devextreme-vue";

export default {
  data() {
    return {
      paymentsIncome: [],
      paymentsExpanses: [],
      filterPopupVisible: false
    };
  },
  mounted() {
    fetch("http://localhost:8085/iea/api/evaluation/expenses")
      .then(
        async function(response) {
          let tmp = await response.json();
          this.paymentsExpanses = tmp;
        }.bind(this)
      )
      .catch(err => console.log(err.message));
    fetch("http://localhost:8085/iea/api/evaluation/income")
      .then(
        async function(response) {
          let tmp = await response.json();
          this.paymentsIncome = tmp;
          console.log(this.paymentsIncome);
        }.bind(this)
      )
      .catch(err => console.log(err.message));
  },
  components: {
    DxPieChart,
    DxSize,
    DxSeries,
    DxLabel,
    DxConnector,
    DxPopup
  },
  methods: {
    pointClickHandler(e) {
      this.toggleVisibility(e.target);
    },
    legendClickHandler(e) {
      let arg = e.target,
        item = e.component.getAllSeries()[0].getPointsByArg(arg)[0];
      this.toggleVisibility(item);
    },
    toggleVisibility(item) {
      item.isVisible() ? item.hide() : item.show();
    },
    showFilterPayments() {
      this.filterPopupVisible = true;
    },
    filterPaymentes() {
      //TODO
    }
  }
};
</script>

<style scoped>
.md-layout-item {
  flex: auto !important;
}
.half {
  width: 50%;
}
.full {
  width: 100%;
}
.right {
  float: right;
}
</style>