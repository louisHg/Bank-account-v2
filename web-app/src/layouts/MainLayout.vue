<template>
  <q-layout view="hHh lpr fFf">
    <q-header class="bg-dark text-white" elevated>
      <q-toolbar>
        <q-btn dense flat round icon="menu" @click="toggleLeftDrawer" />
        <q-toolbar-title
          align="center"
          class="extra-bold titre pointer"
          @click="goToHome()"
        >
          <span class="text-primary extra-bold titre">B</span>ank
          <span class="text-yellow extra-bold titre">A</span>ccount
          <span class="text-red extra-bold titre"> M</span>anager
        </q-toolbar-title>

        <q-item class="cursor-not-allowed" align="right">
          <q-icon name="notifications" :size="size" class="text-white" />
          <q-tooltip> Notifications </q-tooltip>
        </q-item>
      </q-toolbar>
    </q-header>

    <q-drawer
      v-model="leftDrawerOpen"
      :width="150"
      :breakpoint="700"
      side="left"
      bordered
      class="bg-white"
    >
      <q-list>
        <q-item class="item-menu" clickable to="/home">
          <q-icon
            :key="size"
            :size="size"
            name="home"
            class="q-pr-sm"
          />
          <q-item-section>
            <q-item-label>Home</q-item-label>
          </q-item-section>
        </q-item>

        <q-item class="item-menu" clickable to="/users">
          <q-icon
            :key="size"
            :size="size"
            name="folder_shared"
            class="q-pr-sm"
          />
          <q-item-section>
            <q-item-label>Users</q-item-label>
          </q-item-section>
        </q-item>

        <q-item class="item-menu" clickable to="/transactions">
          <q-icon
            :key="size"
            :size="size"
            name="calendar_month"
            class="q-pr-sm"
          />
          <q-item-section>
            <q-item-label>Transactions</q-item-label>
          </q-item-section>
        </q-item>
      </q-list>
    </q-drawer>

    <q-page-container class="bg-grey">
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import { ref } from "vue";

export default {
  components: {},

  setup() {
    const leftDrawerOpen = ref(true);

    return {
      leftDrawerOpen,
      toggleLeftDrawer() {
        leftDrawerOpen.value = !leftDrawerOpen.value;
      },
    };
  },

  methods: {
    goToHome() {
      this.$router.push(`/`);
    },
  },

  data() {
    return {
      size: "xl",
    };
  },
};
</script>
