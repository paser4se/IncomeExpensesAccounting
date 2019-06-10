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

export default {
  data() {
    return {
      payments: [
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
      ]
    };
  }
};
</script>

<style scoped>
</style>
