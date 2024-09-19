<template>
  <apexchart
    height="300"
    type="line"
    :options="options"
    :series="series"
    key="apex-line-chart"
  ></apexchart>
</template>

<script>
import { defineComponent, watch } from "vue";
import { getCssVar } from "quasar";

export default defineComponent({
  name: "Apex-line",
  props: {
    chartCategories: null,
    chartData: null,
  },
  data() {
    return {
      options: {
        title: {
          text: "Cash flow",
          align: "left",
        },
        chart: {
          id: "apex-line",
        },
        colors: [
          getCssVar("primary"),
          getCssVar("secondary"),
          getCssVar("negative"),
        ],
        markers: {
          size: 4,
          hover: {
            sizeOffset: 6,
          },
        },
        xaxis: {
          categories: this.chartCategories,
        },
      },
      series: [
        {
          name: "series-1",
          data: this.chartData,
        },
      ],
    };
  },
  watch: {
    // Watch for changes in chartCategories or chartData props
    chartCategories(newVal) {
      this.options.xaxis.categories = newVal;
    },
    chartData(newVal) {
      this.series[0].data = newVal;
    },
  },
});
</script>
