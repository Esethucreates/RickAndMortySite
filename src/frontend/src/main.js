import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";

// PrimeVue UI
import PrimeVue from "primevue/config";
import Lara from "@primeuix/themes/lara";

const app = createApp(App);

app.use(router);
app.use(PrimeVue, {
  theme: {
    preset: Lara,
    options: {
      darkModeSelector: false || "none",
    },
  },
});

app.mount("#app");
