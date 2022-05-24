import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      component: resolve => require(["../pages/Login.vue"], resolve)
    },

    {
      path: "/Home",
      component: resolve => require(["../components/Home.vue"], resolve),
      children: [
        {
          path: "/Info",
          component: resolve => require(["../pages/InfoPage.vue"], resolve)
        },
        {
          path: "/Consumer",
          component: resolve => require(["../pages/ConsumerPage.vue"], resolve)
        },
        {
          path: "/Writer",
          component: resolve => require(["../pages/WriterPage.vue"], resolve)
        },
        {
          path: "/Book",
          component: resolve => require(["../pages/BookPage.vue"], resolve)
        },
        {
          path: "/Directory",
          component: resolve => require(["../pages/DirectoryPage.vue"], resolve)
        },
        {
          path: "/Collect",
          component: resolve => require(["../pages/CollectPage.vue"], resolve)
        },
        {
          path: "/Classify",
          component: resolve => require(["../pages/Classify.vue"], resolve)
        }
      ]
    }
  ]
});
