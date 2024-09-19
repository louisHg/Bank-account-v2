<template>
  <q-input
    filled
    :value="inputValue"
    :label="inputLabel"
    :type="inputType"
    :style="{
      width: inputWidthValue + '%',
    }"
    @input="updateInput"
  />
</template>

<script>
export default {
  name: "TheInput",
  props: {
    inputValue: { type: String, default: "" },
    inputLabel: { type: String, default: "" },
    inputWidthValue: { type: Number, default: 100 },
    inputType: { type: String, default: "" },
  },
  methods: {
    updateInput(value) {
      // Emit the update event to inform the parent component about the change
      this.$emit("inputValue", value);
    },
  },
};
</script>
