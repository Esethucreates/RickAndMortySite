<script setup>
import { ref, onMounted } from "vue";
import { locationService } from "@/services/API";
import { useRouter } from "vue-router";
// Reactive state
const locations = ref([]);
const info = ref({});
const isLoading = ref(true);
const error = ref(null);
const currentPage = ref(1);
const router = useRouter();

// Fetch locations
const fetchLocations = async (page = 1) => {
  isLoading.value = true;
  try {
    const response = await locationService.getAllLocations(page);
    locations.value = response.data.locationList || [];
    info.value = response.data.info || {};
    currentPage.value = page;
  } catch (err) {
    error.value = "Failed to load Location list. Please try again later.";
    console.error("Error fetching characters:", err);
  } finally {
    isLoading.value = false;
  }
};

const goToNextPage = () => {
  if (info.value.next) {
    fetchLocations(currentPage.value + 1);
  }
};

const goToPrevPage = () => {
  if (info.value.prev !== null && currentPage.value > 1) {
    fetchLocations(currentPage.value - 1);
  }
};

const goToDetails = (id) => {
  router.push(`/locations/${id}`);
};

onMounted(() => {
  fetchLocations();
});
</script>

<template>
  <div class="locations-container">
    <h1>Locations</h1>

    <!-- Loading state -->
    <div v-if="isLoading" class="loading-state">
      <div class="spinner"></div>
      <p>Loading locations...</p>
    </div>

    <!-- Error state -->
    <div v-else-if="error" class="error-state">
      <p>{{ error }}</p>
      <button @click="fetchLocations(currentPage)">Try Again</button>
    </div>

    <!-- Locations grid -->
    <div v-else class="locations-grid">
      <div
        v-for="location in locations"
        :key="location.id"
        class="location-card"
      >
        <div class="location-info">
          <h3 class="location-name">{{ location.name }}</h3>
          <div class="location-details">
            <div class="detail-item">
              <span class="detail-label">Type:</span>
              <span class="detail-value">{{ location.type }}</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">Dimension:</span>
              <span class="detail-value">{{ location.dimension }}</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">Residents:</span>
              <span class="detail-value">{{
                location.residents?.length || 0
              }}</span>
            </div>

            <div class="episode-actions">
              <button
                @click="goToDetails(location.id)"
                class="view-details-btn"
              >
                View Details
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Empty state -->
    <div
      v-if="!isLoading && !error && locations.length === 0"
      class="empty-state"
    >
      <p>No locations found.</p>
    </div>

    <!-- Pagination -->
    <div v-if="!isLoading && !error && locations.length > 0" class="pagination">
      <button
        @click="goToPrevPage"
        :disabled="!info.prev || currentPage === 1"
        class="pagination-btn"
      >
        Previous
      </button>
      <span class="page-info">Page {{ currentPage }}</span>
      <button
        @click="goToNextPage"
        :disabled="!info.next"
        class="pagination-btn"
      >
        Next
      </button>
    </div>
  </div>
</template>

<style scoped>
.view-details-btn {
  padding: 6px 12px;
  background-color: #2ecc71;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.view-details-btn:hover {
  background-color: #27ae60;
}

.locations-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

h1 {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
  font-size: 2rem;
}

.loading-state,
.error-state,
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 300px;
}

.spinner {
  width: 50px;
  height: 50px;
  border: 5px solid #f3f3f3;
  border-top: 5px solid #3498db;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 20px;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.error-state button {
  margin-top: 15px;
  padding: 8px 16px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.locations-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.location-card {
  background-color: #fff;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  padding: 20px;
}

.location-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.location-name {
  margin: 0 0 15px 0;
  font-size: 1.4rem;
  color: #2c3e50;
  border-bottom: 2px solid #f1f1f1;
  padding-bottom: 10px;
}

.location-details {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.detail-item {
  display: flex;
  align-items: baseline;
}

.detail-label {
  font-weight: 600;
  color: #7f8c8d;
  min-width: 90px;
}

.detail-value {
  color: #2c3e50;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 30px;
}

.pagination-btn {
  padding: 8px 16px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.pagination-btn:hover:not(:disabled) {
  background-color: #2980b9;
}

.pagination-btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.page-info {
  margin: 0 15px;
  font-weight: 500;
}

@media (max-width: 768px) {
  .locations-grid {
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  }
}

@media (max-width: 480px) {
  .locations-grid {
    grid-template-columns: 1fr;
  }
}
</style>
