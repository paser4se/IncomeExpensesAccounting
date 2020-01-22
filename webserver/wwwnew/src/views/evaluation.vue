<template>
  <div>
    <h2 class="content-block">Evaluation</h2>
    <div class="content-block">
      <div class="dx-card responsive-paddings">
        <md-empty-state
          md-rounded
          md-icon="report_problem"
          md-label="No commited payments!"
          md-description="The evaluation of commited payments will show up here."
          v-if="this.paymentsExpenses.length == 0 && this.paymentsIncome.length == 0"
        ></md-empty-state>
        <div
          class="md-layout md-gutter"
          v-if="this.paymentsExpenses.length != 0 || this.paymentsIncome.length != 0"
        >
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
              :data-source="paymentsExpenses"
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
          :width="500"
          :height="260"
          v-bind:title="'Filter payments'"
        >
          <div class="dx-field">
            <div class="dx-field-label">From:</div>
            <div class="dx-field-value">
              <dx-date-box :value.sync="from" type="date"/>
            </div>
          </div>
          <div class="dx-field">
            <div class="dx-field-label">To:</div>
            <div class="dx-field-value">
              <dx-date-box :value.sync="to" type="date"/>
            </div>
          </div>
          <md-button class="md-raised md-primary right" @click="filterPaymentes()">Apply</md-button>
          <md-button class="md-raised right" @click="resetPayments()">Reset</md-button>
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
import { DxPopup, DxDateBox } from "devextreme-vue";

export default {
  data() {
    return {
      paymentsIncome: [],
      paymentsExpenses: [],
      filterPopupVisible: false,
      from: new Date(),
      to: new Date()
    };
  },
  mounted() {
    fetch("http://localhost:8080/evaluation/expenses", {
      method: "GET",
      credentials: "include"
    })
      .then(
        async function(response) {
          let tmp = await response.json();
          this.paymentsExpenses = tmp;
        }.bind(this)
      )
      .catch(err => console.log(err.message));
    fetch("http://localhost:8080/evaluation/income", {
      method: "GET",
      credentials: "include"
    })
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
    DxPopup,
    DxDateBox
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
      this.filterPopupVisible = false;
      console.log(this.dateToString(this.from));
      var filter = {
        from: this.dateToString(this.from),
        to: this.dateToString(this.to)
      };
      fetch("http://localhost:8080/evaluation/filterincome", {
        method: "POST",
        headers: {
          "Content-Type": "text/plain"
        },
        credentials: "include",
        body: JSON.stringify(filter)
      }).then(
        async function(response) {
          let tmp = await response.json();
          this.paymentsIncome = tmp;
          console.log(tmp);
        }.bind(this)
      );
      fetch("http://localhost:8080/evaluation/filterexpenses", {
        method: "POST",
        headers: {
          "Content-Type": "text/plain"
        },
        credentials: "include",
        body: JSON.stringify(filter)
      }).then(
        async function(response) {
          let tmp = await response.json();
          this.paymentsExpenses = tmp;
          console.log(tmp);
        }.bind(this)
      );
    },
    dateToString(date) {
      var mm = date.getMonth() + 1; // getMonth() is zero-based
      var dd = date.getDate();

      return [
        date.getFullYear(),
        (mm > 9 ? "" : "0") + mm,
        (dd > 9 ? "" : "0") + dd
      ].join("/");
    },
    resetPayments() {
      this.filterPopupVisible = false;
      fetch("http://localhost:8080/evaluation/expenses", {
        method: "GET",
        credentials: "include"
      })
        .then(
          async function(response) {
            let tmp = await response.json();
            this.paymentsExpenses = tmp;
          }.bind(this)
        )
        .catch(err => console.log(err.message));
      fetch("http://localhost:8080/evaluation/income", {
        method: "GET",
        credentials: "include"
      })
        .then(
          async function(response) {
            let tmp = await response.json();
            this.paymentsIncome = tmp;
            console.log(this.paymentsIncome);
          }.bind(this)
        )
        .catch(err => console.log(err.message));
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
.dx-field-label {
  color: black !important;
  font-size: 20px !important;
}
</style>