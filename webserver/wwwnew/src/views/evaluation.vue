<template>
  <div>
    <chartjs-dougnut v-bind:labels="labels" v-bind:option="option"></chartjs-dougnut>
  </div>
</template>

<script>
export default {
  data() {
    return {
      labels: ["Apples", "Bananas", "Grapes"],
      datasets: [
        {
          data: [20, 30, 50],
          backgroundColor: ["Red", "Yellow", "Purple"]
        }
      ],
      option: {
        title: {
          display: true,
          position: "bottom",
          text: "Fruits"
        }
      }
    };
  }
};
</script>

<style scoped>
</style>
