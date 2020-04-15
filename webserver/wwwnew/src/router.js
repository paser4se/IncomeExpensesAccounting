import Settings from './views/settings';
import Evaluation from './views/evaluation';
import Payments from './views/payments';
import Vue from "vue";
import Router from "vue-router";

import auth from "./auth";

import Home from "./views/home";
import Profile from "./views/profile";
import defaultLayout from "./layouts/side-nav-outer-toolbar";
import simpleLayout from "./layouts/single-card";

Vue.use(Router);

const router = new Router({
  routes: [
    {
      path: "/home",
      name: "home",
      meta: { requiresAuth: true },
      components: {
        layout: defaultLayout,
        content: Home
      }
    },
    {
      path: "/profile",
      name: "profile",
      meta: { requiresAuth: true },
      components: {
        layout: defaultLayout,
        content: Profile
      }
    },
    {
      path: "/login",
      name: "login",
      meta: { requiresAuth: false },
      components: {
        layout: simpleLayout,
        content: () =>
          import("./views/login-form")
      }
    },
    {
      path: "/register",
      name: "register",
      meta: { requiresAuth: false },
      components: {
        layout: simpleLayout,
        content: () =>
          import("./views/register-form")
      }
    },
    {
      path: "/",
      redirect: "/home"
    },
    {
      path: "/recovery",
      redirect: "/home"
    },
    {
      path: "*",
      redirect: "/home"
    },
    {
      path: "/payments",
      name: "payments",
      meta: { requiresAuth: true },
      components: {
        layout: defaultLayout,
        content: Payments
      }
    },
    {
      path: "/evaluation",
      name: "evaluation",
      meta: { requiresAuth: true },
      components: {
        layout: defaultLayout,
        content: Evaluation
      }
    }, {
      path: "/settings",
      name: "settings",
      meta: { requiresAuth: true },
      components: {
        layout: defaultLayout,
        content: Settings
      }
    }]
});

router.beforeEach((to, from, next) => {
  auth.authenticate().then(res => {
    if (res.status != 200 && to.meta.requiresAuth)
      next({ path: '/login', query: { origin: from.path } })
    else
      next()
  });
});

export default router;
