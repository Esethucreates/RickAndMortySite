<script setup>
import { useRouter } from "vue-router";
import Button from "primevue/button";
import Card from "primevue/card";

const router = useRouter();

const goHome = () => {
  router.push("/");
};
</script>

<template>
  <div class="container">
    <Card class="not-found-card">
      <template #header>
        <div class="error-icon">
          <i class="pi pi-exclamation-circle"></i>
        </div>
      </template>

      <template #title>
        <h1 class="error-title">404 Page Not Found</h1>
      </template>

      <template #subtitle>
        <p class="error-subtitle">
          Oops! The page you're looking for doesn't exist.
        </p>
      </template>

      <template #content>
        <p class="error-message">
          You may have mistyped the address or the page may have moved.
        </p>
      </template>

      <template #footer>
        <div class="action-buttons">
          <Button
            label="Go Back"
            icon="pi pi-arrow-left"
            outlined
            @click="router.back()"
            class="p-button-secondary mr-2"
          />
          <Button label="Go Home" icon="pi pi-home" @click="goHome" />
        </div>
      </template>
    </Card>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 80vh;
  padding: 2rem;
}

.not-found-card {
  max-width: 500px;
  width: 100%;
  text-align: center;
}

.error-icon {
  display: flex;
  justify-content: center;
  padding: 2rem 0;
}

.error-icon i {
  font-size: 5rem;
  color: #f59e0b;
}

.error-title {
  font-size: 2rem;
  margin: 0;
  color: #1e293b;
}

.error-subtitle {
  font-size: 1.25rem;
  color: #64748b;
  margin: 0.5rem 0;
}

.error-message {
  color: #64748b;
  margin: 1.5rem 0;
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 1rem;
}

@media (max-width: 576px) {
  .error-icon i {
    font-size: 4rem;
  }

  .error-title {
    font-size: 1.5rem;
  }

  .action-buttons {
    flex-direction: column;
    gap: 0.5rem;
  }

  .action-buttons .p-button {
    width: 100%;
    margin-right: 0 !important;
    margin-bottom: 0.5rem;
  }
}
</style>
