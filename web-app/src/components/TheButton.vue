<template>
  <div>
    <q-btn
      :color="btnCouleur"
      :to="btnRedirection"
      :text-color="btnTexteColor"
      :outline="btnOutline"
    >
      <q-icon v-if="btnWithIcon" :name="btnIcone" class="q-mr-xs" size="sm" />
      <span class="accentue">{{ btnTexte }}</span>
    </q-btn>
  </div>
</template>

<script>
export default {
  name: "TheButton",
  props: {
    btnTexte: { type: String, default: "" },
    btnRedirection: { type: String, default: "" },

    btnCouleur: { type: String, default: "primary" },
    btnTexteColor: { type: String, default: "" },
    btnOutline: { type: Boolean, default: false },

    btnWithIcon: { type: Boolean, default: true },
    btnIcone: { type: String, default: "" },
  },
};
</script>
