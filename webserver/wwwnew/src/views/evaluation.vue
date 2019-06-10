<template>
  <div>
    <h2 class="content-block">Evaluation</h2>
    <div class="content-block">
      <div class="dx-card responsive-paddings">
        <dx-pie-chart
          id="pie"
          :data-source="areas"
          palette="Bright"
          title="Area of Countries"
          @point-click="pointClickHandler($event)"
          @legend-click="legendClickHandler($event)"
        >
          <dx-series argument-field="country" value-field="area">
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
      areas: [
        {
          country: "Russia",
          area: 12
        },
        {
          country: "Canada",
          area: 7
        },
        {
          country: "USA",
          area: 7
        },
        {
          country: "China",
          area: 7
        },
        {
          country: "Brazil",
          area: 6
        },
        {
          country: "Australia",
          area: 5
        },
        {
          country: "India",
          area: 2
        },
        {
          country: "Others",
          area: 55
        }
      ]
    };
  }
};
</script>

<style scoped>
</style>
