<template>
  <div>
    <q-btn unelevated size="md" bg-color="transparent" class="q-px-sm">
      <q-icon :name="btnIcone" :color="btnCouleur" :size="btnSize" />
      <q-tooltip v-if="btnTooltip"> {{ btnTooltip }} </q-tooltip>
    </q-btn>
  </div>
</template>

<script>
export default {
  name: "TheButtonIcon",
  props: {
    btnIcone: { type: String, default: "" },
    btnCouleur: { type: String, default: "" },
    btnSize: { type: String, default: "md" },
    btnTooltip: { type: String, default: null },
  },
};
</script>
