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
                <v-card class="mb-5" color="grey lighten-1" height="200px"></v-card>
                <v-btn @click="e1 = 1">Cancel</v-btn>
                <v-btn color="primary" @click="e1 = 2">Continue</v-btn>
              </v-stepper-content>
              <v-stepper-content step="2">
                <v-card class="mb-5" color="grey lighten-1" height="200px"></v-card>
                <v-btn @click="e1 = 1">Cancel</v-btn>
                <v-btn color="primary" @click="e1 = 3">Continue</v-btn>
              </v-stepper-content>
              <v-stepper-content step="3">
                <v-card class="mb-5" color="grey lighten-1" height="200px"></v-card>
                <v-btn @click="e1 = 2">Cancel</v-btn>
                <v-btn color="primary" @click="e1 = 1">Continue</v-btn>
              </v-stepper-content>
            </v-stepper-items>
          </v-stepper>
        </v-app>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      e1: 0
    };
  }
};
</script>

<style lang="scss">
</style>