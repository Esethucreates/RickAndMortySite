<script setup>
import { ref, onMounted } from "vue";
import { characterService } from "@/services/API";
import { useRouter } from "vue-router";

// Reactive state
const characters = ref([]);
const info = ref({});
const isLoading = ref(true);
const error = ref(null);
const currentPage = ref(0);
const router = useRouter();

// Fetch characters data
const fetchCharacters = async (page = 1) => {
  isLoading.value = true;
  try {
    const response = await characterService.getAllCharacters(page);
    characters.value = response.data.characterList || [];
    info.value = response.data.info || {};
    currentPage.value = page;
  } catch (err) {
    error.value = "Failed to load characters. Please try again later.";
    console.error("Error fetching characters:", err);
  } finally {
    isLoading.value = false;
  }
};

// Navigation methods
const goToNextPage = () => {
  if (info.value.next) {
    fetchCharacters(currentPage.value + 1);
  }
};

const goToPrevPage = () => {
  if (info.value.prev !== null && currentPage.value > 1) {
    fetchCharacters(currentPage.value - 1);
  }
};

const goToDetails = (id) => {
  router.push(`/characters/${id}`);
};

// Load characters on component mount
onMounted(() => {
  fetchCharacters();
});
</script>

<template>
  <div class="characters-container">
    <h1>Character List</h1>

    <!-- Loading state -->
    <div v-if="isLoading" class="loading-state">
      <div class="spinner"></div>
      <p>Loading characters...</p>
    </div>

    <!-- Error state -->
    <div v-else-if="error" class="error-state">
      <p>{{ error }}</p>
      <button @click="fetchCharacters(currentPage)">Try Again</button>
    </div>

    <!-- Characters grid -->
    <div v-else class="characters-grid">
      <div
        v-for="character in characters"
        :key="character.id"
        class="character-card"
      >
        <img
          :src="character.image"
          :alt="character.name"
          class="character-image"
        />
        <div class="character-info">
          <h3>{{ character.name }}</h3>
          <div class="character-details">
            <span
              class="status-indicator"
              :class="character.status.toLowerCase()"
            ></span>
            <p>{{ character.status }} - {{ character.species }}</p>
          </div>
          <div class="character-location">
            <p class="location-label">Last known location:</p>
            <p>{{ character.location?.name }}</p>
          </div>
          <div class="character-origin">
            <p class="origin-label">Origin:</p>
            <p>{{ character.origin?.name }}</p>
          </div>

          <div class="episode-actions">
            <button @click="goToDetails(character.id)" class="view-details-btn">
              View Details
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Empty state -->
    <div
      v-if="!isLoading && !error && characters.length === 0"
      class="empty-state"
    >
      <p>No characters found.</p>
    </div>

    <!-- Pagination -->
    <div
      v-if="!isLoading && !error && characters.length > 0"
      class="pagination"
    >
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

.characters-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

h1 {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
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

.characters-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

.character-card {
  background-color: #fff;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.character-card:hover {
  transform: translateY(-5px);
}

.character-image {
  width: 100%;
  height: 220px;
  object-fit: cover;
}

.character-info {
  padding: 15px;
}

.character-info h3 {
  margin: 0 0 10px 0;
  font-size: 1.2rem;
}

.character-details {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.status-indicator {
  display: inline-block;
  width: 10px;
  height: 10px;
  border-radius: 50%;
  margin-right: 8px;
}

.status-indicator.alive {
  background-color: #55cc44;
}

.status-indicator.dead {
  background-color: #d63d2e;
}

.status-indicator.unknown {
  background-color: #9e9e9e;
}

.character-location,
.character-origin {
  margin-top: 10px;
}

.location-label,
.origin-label {
  color: #666;
  font-size: 0.9rem;
  margin-bottom: 2px;
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
}

.pagination-btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.page-info {
  margin: 0 15px;
}

.error-state button {
  margin-top: 10px;
  padding: 8px 16px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>
