<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { episodeService } from "@/services/API";

// Reactive state
const route = useRoute();
const router = useRouter();

const episode = ref(null);
const loading = ref(true);
const error = ref(null);

const fetchEpisodeData = async () => {
  loading.value = true;
  error.value = null;

  try {
    const episodeId = parseInt(route.params.id);
    const response = await episodeService.getEpisode(episodeId);
    episode.value = response.data;
  } catch (err) {
    console.error("Error fetching episode details:", err);
    error.value = err.message || "Failed to load episode details";
  } finally {
    loading.value = false;
  }
};

const formatDate = (dateString) => {
  if (!dateString) return "Unknown";

  try {
    const date = new Date(dateString);
    return date.toLocaleDateString("en-US", {
      year: "numeric",
      month: "long",
      day: "numeric",
    });
  } catch (e) {
    return dateString; // Return original if parsing fails
  }
};

const goBack = () => {
  router.push("/episodes");
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

const goTo = (id) => {
  router.push(`/characters/${id}`);
};

onMounted(() => {
  fetchEpisodeData();
});
</script>
<template>
  <div class="episode-container">
    <div v-if="loading" class="loading">
      <p>Loading episode details...</p>
    </div>

    <div v-else-if="error" class="error">
      <p>Error: {{ error }}</p>
      <button @click="fetchEpisodeData" class="retry-btn">Retry</button>
    </div>

    <div v-else-if="episode" class="episode-details">
      <h1>{{ episode.name }}</h1>
      <div class="episode-info">
        <span class="episode-code">{{ episode.episode }}</span>
        <span class="air-date">Aired: {{ formatDate(episode.air_date) }}</span>
      </div>

      <div class="detail-section">
        <div class="detail-item">
          <h3>Episode ID</h3>
          <p>{{ episode.id }}</p>
        </div>
      </div>

      <div class="characters-section">
        <h3>Characters ({{ episode.characters.length }})</h3>
        <div v-if="episode.characters.length === 0" class="no-characters">
          <p>No characters found for this episode.</p>
        </div>

        <div v-else class="characters-list">
          <div
            v-for="(character, index) in episode.characters"
            :key="index"
            class="character-item"
            @click="goTo(extractIdFromUrl(character))"
          >
            Character {{ extractIdFromUrl(character) }}
          </div>
        </div>
      </div>

      <div class="episode-actions">
        <button @click="goBack" class="back-btn">Back to Episodes</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.episode-container {
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

.episode-details h1 {
  font-size: 28px;
  margin-bottom: 10px;
  color: #333;
}

.episode-info {
  display: flex;
  gap: 15px;
  margin-bottom: 24px;
  align-items: center;
}

.episode-code {
  background-color: #4caf50;
  color: white;
  padding: 5px 10px;
  border-radius: 4px;
  font-weight: 500;
  font-family: monospace;
}

.air-date {
  color: #666;
  font-style: italic;
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

.characters-section {
  margin-bottom: 30px;
}

.characters-section h3 {
  font-size: 20px;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.characters-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  margin-top: 10px;
  gap: 12px;
}

.character-item {
  background-color: #89bb16;
  color: #ffffff;
  padding: 8px 12px;
  border-radius: 4px;
  text-align: center;
  font-size: 0.9rem;
  cursor: pointer;
}

.character-item:hover {
  background-color: #455f07;
}

.no-characters {
  color: #666;
  font-style: italic;
}

.episode-actions {
  margin-top: 30px;
  text-align: center;
}

.back-btn {
  background-color: #ff9800;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.back-btn:hover {
  background-color: #f57c00;
}
</style>
