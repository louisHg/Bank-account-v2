const routes = [
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      { path: "/", component: () => import("pages/IndexPage.vue") },
      {
        path: "/home",
        component: () => import("pages/IndexPage.vue"),
      },
      {
        path: "/users",
        component: () => import("pages/UsersPage.vue"),
      },
      {
        path: "/transactions",
        component: () => import("pages/TransactionsPage.vue"),
      },
      {
        path: "/account/:userId",
        component: () => import("pages/AccountPage.vue"),
      },
    ],
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/ErrorNotFound.vue"),
  },
];

export default routes;
