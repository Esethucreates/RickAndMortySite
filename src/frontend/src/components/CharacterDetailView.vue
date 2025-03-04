<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { characterService } from "@/services/API";

const route = useRoute();
const router = useRouter();
const character = ref(null);
const isLoading = ref(true);
const error = ref(null);

// Fetch the character data based on the ID from the route params
const fetchCharacter = async () => {
  const charId = route.params.id;
  if (!charId) {
    error.value = "Character ID is missing";
    isLoading.value = false;
    return;
  }

  try {
    isLoading.value = true;
    const response = await characterService.getCharacter(charId);
    character.value = response.data;
  } catch (err) {
    console.error("Error fetching character:", err);
    error.value = "Failed to load character details. Please try again later.";
  } finally {
    isLoading.value = false;
  }
};

// Helper function to extract episode number from URL
const getEpisodeNumber = (episodeUrl) => {
  if (!episodeUrl) return "";
  const matches = episodeUrl.match(/\/episode\/(\d+)$/);
  return matches ? matches[1] : "";
};

// Navigate back to characters list
const goBackToList = () => {
  router.push("/characters");
};

const goTo = (id) => {
  router.push(`/episodes/${id}`);
};

const extractIdFromUrl = (url) => {
  if (!url || typeof url !== "string") return "";

  const cleanUrl = url.endsWith("/") ? url.slice(0, -1) : url;

  const parts = cleanUrl.split("/");
  return parts[parts.length - 1];
};

onMounted(() => {
  fetchCharacter();
});
</script>

<template>
  <div class="character-details-container">
    <button @click="goBackToList" class="back-button">
      ← Back to Characters
    </button>

    <!-- Loading state -->
    <div v-if="isLoading" class="loading-state">
      <div class="spinner"></div>
      <p>Loading character details...</p>
    </div>

    <!-- Error state -->
    <div v-else-if="error" class="error-state">
      <p>{{ error }}</p>
      <button @click="fetchCharacter" class="retry-button">Try Again</button>
    </div>

    <!-- Character details -->
    <div v-else-if="character" class="character-info">
      <div class="character-header">
        <div class="character-image-container">
          <img
            :src="character.image"
            :alt="character.name"
            class="character-image"
          />
        </div>
        <div class="character-header-details">
          <h1 class="character-name">{{ character.name }}</h1>
          <div class="character-status">
            <span
              class="status-indicator"
              :class="character.status?.toLowerCase()"
            ></span>
            <span>{{ character.status }} - {{ character.species }}</span>
            <span v-if="character.type">({{ character.type }})</span>
          </div>
          <div class="character-gender">
            <strong>Gender:</strong> {{ character.gender }}
          </div>
        </div>
      </div>

      <div class="character-sections">
        <div class="character-section">
          <h2 class="section-title">Location</h2>
          <div class="section-content">
            <div class="detail-row">
              <span class="detail-label">Origin:</span>
              <span class="detail-value">{{
                character.origin?.name || "Unknown"
              }}</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">Last known location:</span>
              <span class="detail-value link-decorate">
                <router-link
                  :to="`/locations/${extractIdFromUrl(
                    character.location?.url || null
                  )}`"
                >
                  {{ character.location?.name || "Unknown" }}
                </router-link>
              </span>
            </div>
          </div>
        </div>

        <div
          class="character-section"
          v-if="character.episode && character.episode.length > 0"
        >
          <h2 class="section-title">Episodes</h2>
          <div class="section-content">
            <p>Appeared in {{ character.episode.length }} episode(s):</p>

            <div class="episodes-list">
              <div
                v-for="(episodeUrl, index) in character.episode"
                :key="index"
                @click="goTo(getEpisodeNumber(episodeUrl))"
                class="episode-item"
              >
                Episode {{ getEpisodeNumber(episodeUrl) }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- No character found -->
    <div v-else class="empty-state">
      <p>No character found with the provided ID.</p>
      <button @click="goBackToList" class="back-button">
        Go Back to Characters
      </button>
    </div>
  </div>
</template>

<style scoped>
.character-details-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

.back-button {
  display: inline-flex;
  align-items: center;
  padding: 8px 16px;
  background-color: #f1f1f1;
  border: none;
  border-radius: 4px;
  font-weight: 500;
  cursor: pointer;
  margin-bottom: 20px;
  transition: background-color 0.2s;
}

.back-button:hover {
  background-color: #e0e0e0;
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

.retry-button {
  margin-top: 15px;
  padding: 8px 16px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.character-info {
  background-color: #fff;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.character-header {
  display: flex;
  padding: 20px;
  background-color: #f8f9fa;
  border-bottom: 1px solid #e9ecef;
}

.character-image-container {
  flex-shrink: 0;
  margin-right: 20px;
}

.character-image {
  width: 200px;
  height: 200px;
  border-radius: 10px;
  object-fit: cover;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.character-header-details {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.character-name {
  margin: 0 0 15px 0;
  font-size: 2rem;
  color: #2c3e50;
}

.character-status {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  font-size: 1.1rem;
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

.character-gender {
  font-size: 1.1rem;
}

.character-sections {
  padding: 20px;
}

.character-section {
  margin-bottom: 30px;
}

.character-section:last-child {
  margin-bottom: 0;
}

.section-title {
  font-size: 1.5rem;
  color: #2c3e50;
  margin: 0 0 15px 0;
  padding-bottom: 8px;
  border-bottom: 2px solid #e9ecef;
}

.section-content {
  padding: 0 10px;
}

.detail-row {
  display: flex;
  margin-bottom: 10px;
}

.detail-row:last-child {
  margin-bottom: 0;
}

.detail-label {
  font-weight: 600;
  min-width: 150px;
  color: #7f8c8d;
}

.detail-value {
  color: #2c3e50;
}

.episodes-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
  gap: 10px;
  margin-top: 10px;
}

.episode-item {
  background-color: #3498db;
  color: white;
  padding: 8px 12px;
  border-radius: 4px;
  text-align: center;
  font-size: 0.9rem;
  cursor: pointer;
}
.episode-item:hover {
  background-color: #166da7;
}

@media (max-width: 768px) {
  .character-header {
    flex-direction: column;
    align-items: center;
    text-align: center;
  }

  .character-image-container {
    margin-right: 0;
    margin-bottom: 20px;
  }

  .detail-row {
    flex-direction: column;
  }

  .detail-label {
    margin-bottom: 5px;
  }

  .link-decorate {
    text-decoration: none;
  }
}
</style>
