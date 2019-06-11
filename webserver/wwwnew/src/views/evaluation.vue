<template>
  <div>
    <h2 class="content-block">Evaluation</h2>
    <div class="content-block">
      <div class="dx-card responsive-paddings">
        <dx-pie-chart
          id="pie"
          :data-source="payments"
          palette="Bright"
          title="Payments"
          @point-click="pointClickHandler($event)"
          @legend-click="legendClickHandler($event)"
        >
          <dx-series argument-field="category" value-field="amount">
            <dx-label :visible="true">
              <dx-connector :visible="true" :width="1"/>
            </dx-label>
          </dx-series>
          <dx-size :width="500"/>
          <dx-export :enabled="true"/>
        </dx-pie-chart>
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
  DxConnector,
  DxExport
} from "devextreme-vue/pie-chart";
import axios from "axios";
export default {
  data() {
    return {
      /*payments: [
        {
          category: "Bank",
          amount: 12
        },
        {
          category: "Zalando",
          amount: 7
        },
        {
          category: "Essen",
          amount: 7
        },
        {
          category: "Auto",
          amount: 7
        },
        {
          category: "Studium Tochter",
          amount: 6
        },
        {
          category: "Entertainment",
          amount: 5
        },
        {
          category: "Sonstige",
          amount: 55
        }
      ]*/
      payments: []
    };
  },
  mounted() {
    //var self = this;
    axios
      .get("http://localhost:8085/iea/api/evaluation/expenses")
      .then(function(res) {
        //self.payments = res.data;
        console.log("Data: ", res.data);
      })
      .catch(function(error) {
        console.log("Error: ", error);
      });
  },
  components: {
    DxPieChart,
    DxSize,
    DxSeries,
    DxLabel,
    DxConnector,
    DxExport
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
    }
  }
};
</script>

<style scoped>
</style>