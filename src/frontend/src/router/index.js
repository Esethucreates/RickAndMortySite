import { createRouter, createWebHistory } from "vue-router";

import CharactersView from "@/components/CharactersView.vue";
import CharacterDetailView from "@/components/CharacterDetailView.vue";
import LocationsView from "@/components/LocationsView.vue";
import LocationDetailView from "@/components/LocationDetailView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/characters",
      name: "characters",
      component: CharactersView,
      meta: {
        title: "Rick and Morty - Characters",
      },
    },
    {
      path: "/characters/:id",
      name: "character-detail",
      component: CharacterDetailView,
      props: true,
      meta: {
        title: "Character Details",
      },
    },
    {
      path: "/locations",
      name: "locations",
      component: LocationsView,
      meta: {
        title: "Rick and Morty - Locations",
      },
    },
    {
      path: "/locations/:id",
      name: "location-detail",
      component: LocationDetailView,
      props: true,
      meta: {
        title: "Location Details",
      },
    },
  ],
});


// Update document title based on route meta
router.beforeEach((to, from, next) => {
  document.title = to.meta.title || "Rick and Morty Explorer";
  next();
});

export default router;
