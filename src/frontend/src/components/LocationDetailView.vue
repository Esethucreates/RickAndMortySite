<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { locationService } from "@/services/API";

const route = useRoute();
const router = useRouter();

const location = ref(null);
const loading = ref(true);
const error = ref(null);

const fetchLocationData = async () => {
  loading.value = true;
  error.value = null;

  try {
    const locationId = parseInt(route.params.id);
    const response = await locationService.getLocation(locationId);
    location.value = response.data;
  } catch (err) {
    console.error("Error fetching location details:", err);
    error.value = err.message || "Failed to load location details";
  } finally {
    loading.value = false;
  }
};

const goBack = () => {
  router.push("/locations");
};

const extractIdFromUrl = (url) => {
  // Check if url is a string and not empty
  if (!url || typeof url !== "string") return "";

  // Remove trailing slash if present
  const cleanUrl = url.endsWith("/") ? url.slice(0, -1) : url;

  // Extract the ID from the end of the URL
  const parts = cleanUrl.split("/");
  return parts[parts.length - 1];
};

onMounted(() => {
  fetchLocationData();
});
</script>

<template>
  <div class="location-container">
    <div v-if="loading" class="loading">
      <p>Loading location details...</p>
    </div>

    <div v-else-if="error" class="error">
      <p>Error: {{ error }}</p>
      <button @click="fetchLocationData" class="retry-btn">Retry</button>
    </div>

    <div v-else-if="location" class="location-details">
      <h1>{{ location.name }}</h1>

      <div class="detail-section">
        <div class="detail-item">
          <h3>Location ID</h3>
          <p>{{ location.id }}</p>
        </div>

        <div class="detail-item">
          <h3>Type</h3>
          <p>{{ location.type }}</p>
        </div>

        <div class="detail-item">
          <h3>Dimension</h3>
          <p>{{ location.dimension }}</p>
        </div>
      </div>

      <div class="residents-section">
        <h3>Residents ({{ location.residents.length }})</h3>
        <div v-if="location.residents.length === 0" class="no-residents">
          <p>No residents found.</p>
        </div>

        <ul v-else class="residents-list">
          <li v-for="(resident, index) in location.residents" :key="index">
            <router-link
              :to="`/characters/${extractIdFromUrl(resident)}`"
              class="character-link"
            >
              Character # {{ extractIdFromUrl(resident) }}
            </router-link>
          </li>
        </ul>
      </div>

      <div class="location-actions">
        <button @click="goBack" class="back-btn">Back to Locations</button>
      </div>
    </div>
  </div>
</template>
<style scoped>
.location-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.loading,
.error {
  text-align: center;
  margin: 50px 0;
}

.error {
  color: #d32f2f;
}

.retry-btn {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 10px;
}

.location-details h1 {
  font-size: 28px;
  margin-bottom: 24px;
  color: #333;
}

.detail-section {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  margin-bottom: 30px;
  background-color: #f5f5f5;
  padding: 20px;
  border-radius: 8px;
}

.detail-item {
  flex: 1;
  min-width: 200px;
}

.detail-item h3 {
  font-size: 16px;
  color: #666;
  margin-bottom: 8px;
}

.detail-item p {
  font-size: 18px;
  font-weight: 500;
}

.residents-section {
  margin-bottom: 30px;
}

.residents-section h3 {
  font-size: 20px;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.residents-list {
  list-style-type: none;
  padding: 0;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 10px;
}

.residents-list li {
  padding: 10px 15px;
  background-color: #e3f2fd;
  border-radius: 4px;
  font-size: 14px;
}

.no-residents {
  color: #666;
  font-style: italic;
}

.location-actions {
  margin-top: 30px;
  text-align: center;
}

.back-btn {
  background-color: #2196f3;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.back-btn:hover {
  background-color: #1976d2;
}
</style>
