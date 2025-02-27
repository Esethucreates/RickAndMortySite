<script setup>
import { ref, onMounted } from "vue";
import { episodeService } from "@/services/API";
import { useRouter } from "vue-router";

// Reactive state
const episodes = ref([]);
const info = ref({});
const isLoading = ref(true);
const error = ref(null);
const currentPage = ref(1);
const router = useRouter();

// Fetch episodes
const fetchEpisodes = async (page = 1) => {
  isLoading.value = true;
  try {
    const response = await episodeService.getAllEpisodes(page);
    episodes.value = response.data.episodeList || [];
    info.value = response.data.info || {};
    currentPage.value = page;
  } catch (err) {
    error.value = "Failed to load episodes. Please try again later.";
    console.error("Error fetching episodes:", err);
  } finally {
    isLoading.value = false;
  }
};

// Navigation methods
const goToNextPage = () => {
  if (info.value.next) {
    fetchEpisodes(currentPage.value + 1);
  }
};

const goToPrevPage = () => {
  if (info.value.prev !== null && currentPage.value > 1) {
    fetchEpisodes(currentPage.value - 1);
  }
};

const goToDetails = (id) => {
  router.push(`/episodes/${id}`);
};

// Format episode code (e.g., S01E01)
const formatEpisodeCode = (episode) => {
  if (!episode || !episode.episode) return "";
  return episode.episode;
};

// Format air date
const formatAirDate = (dateString) => {
  if (!dateString) return "Unknown";

  try {
    const date = new Date(dateString);
    return new Intl.DateTimeFormat("en-US", {
      year: "numeric",
      month: "long",
      day: "numeric",
    }).format(date);
  } catch (e) {
    return dateString;
  }
};

// Load episodes on component mount
onMounted(() => {
  fetchEpisodes();
});
</script>

<template>
  <div class="episodes-container">
    <h1>Episodes</h1>

    <!-- Loading state -->
    <div v-if="isLoading" class="loading-state">
      <div class="spinner"></div>
      <p>Loading episodes...</p>
    </div>

    <!-- Error state -->
    <div v-else-if="error" class="error-state">
      <p>{{ error }}</p>
      <button @click="fetchEpisodes(currentPage)">Try Again</button>
    </div>

    <!-- Episodes list -->
    <div v-else class="episodes-list">
      <div v-for="episode in episodes" :key="episode.id" class="episode-card">
        <div class="episode-header">
          <span class="episode-code">{{ formatEpisodeCode(episode) }}</span>
          <h3 class="episode-title">{{ episode.name }}</h3>
        </div>
        <div class="episode-details">
          <div class="detail-row">
            <span class="detail-label">Air Date:</span>
            <span class="detail-value">{{
              formatAirDate(episode.air_date)
            }}</span>
          </div>
          <div class="detail-row">
            <span class="detail-label">Characters:</span>
            <span class="detail-value">{{
              episode.characters?.length || 0
            }}</span>
          </div>
        </div>
        <div class="episode-actions">
          <button @click="goToDetails(episode.id)" class="view-details-btn">
            View Details
          </button>
        </div>
      </div>
    </div>

    <!-- Empty state -->
    <div
      v-if="!isLoading && !error && episodes.length === 0"
      class="empty-state"
    >
      <p>No episodes found.</p>
    </div>

    <!-- Pagination -->
    <div v-if="!isLoading && !error && episodes.length > 0" class="pagination">
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
.episodes-container {
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

.episodes-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.episode-card {
  background-color: #fff;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  padding: 20px;
}

.episode-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.episode-header {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  border-bottom: 2px solid #f1f1f1;
  padding-bottom: 10px;
}

.episode-code {
  background-color: #3498db;
  color: white;
  padding: 4px 8px;
  border-radius: 4px;
  font-weight: 600;
  font-size: 0.9rem;
  margin-right: 10px;
}

.episode-title {
  margin: 0;
  font-size: 1.4rem;
  color: #2c3e50;
  flex-grow: 1;
}

.episode-details {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-bottom: 15px;
}

.detail-row {
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

.episode-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
}

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
  .episode-header {
    flex-direction: column;
    align-items: flex-start;
  }

  .episode-code {
    margin-bottom: 8px;
  }
}
</style>
