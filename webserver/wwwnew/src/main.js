import 'devextreme/dist/css/dx.common.css';
import './themes/generated/theme.base.css';
import './themes/generated/theme.additional.css';
import Vue from "vue";

import "chart.js";
import "hchs-vue-charts";

import App from "./App";
import router from "./router";
import appInfo from "./app-info";
import VueMaterial from 'vue-material';
import 'vue-material/dist/vue-material.min.css';

Vue.config.productionTip = false;
Vue.prototype.$appInfo = appInfo;

new Vue({
  router,
  render: h => h(App)
}).$mount("#app");


Vue.use(VueMaterial);
/*Vue.use(Vuetify, {
  theme: {
    primary: '#288b9e',
    secondary: '#18535e',
    accent: '#8c9eff',
    error: '#b71c1c'
  }
});*/