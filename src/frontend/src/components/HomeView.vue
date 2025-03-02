<script setup>
import { characterService } from "@/services/API";
import { useRouter } from "vue-router";
import { ref, onMounted } from "vue";

// PrimeVue components
import Button from "primevue/button";
import Card from "primevue/card";
import Avatar from "primevue/avatar";
import Tag from "primevue/tag";
import ProgressSpinner from "primevue/progressspinner";
import Message from "primevue/message";

const router = useRouter();
const isLoading = ref(true);
const characterList = ref([]);
const error = ref(null);

const fetchCharacters = async () => {
  isLoading.value = true;
  error.value = null;

  try {
    const response = await characterService.getFeaturedCharacters();
    characterList.value = response.data || [];
  } catch (err) {
    error.value = "Failed to load characters. Please try again later.";
    console.error("Error fetching characters:", err);
  } finally {
    isLoading.value = false;
  }
};

const getStatusSeverity = (status) => {
  if (!status) return "info";

  switch (status.toLowerCase()) {
    case "alive":
      return "success";
    case "dead":
      return "danger";
    case "unknown":
      return "warning";
    default:
      return "info";
  }
};

const goToDetails = (id) => {
  router.push(`/characters/${id}`);
};

onMounted(() => {
  fetchCharacters();
});
</script>

<template>
  <div class="home-container">
    <div class="header-section p-4">
      <h1 class="text-4xl font-bold mb-2">Character Explorer</h1>
      <p class="text-lg text-gray-600 mb-4">
        Discover interesting characters from across the multiverse
      </p>
    </div>

    <!-- Loading State -->
    <div v-if="isLoading" class="loading-container p-6 flex justify-center">
      <ProgressSpinner strokeWidth="4" animationDuration=".5s" />
    </div>

    <!-- Error State -->
    <div v-else-if="error" class="error-container p-6">
      <Message severity="error" :closable="false" class="mb-4">
        <template #content>
          <div>
            <i class="pi pi-times-circle mr-2"></i>
            <span>{{ error }}</span>
          </div>
        </template>
      </Message>
      <div class="flex justify-center mt-4">
        <Button
          label="Try Again"
          icon="pi pi-refresh"
          @click="fetchCharacters"
        />
      </div>
    </div>

    <!-- Content -->
    <div v-else class="content-section p-4">
      <!-- Featured Characters Section -->
      <div class="mb-8">
        <div class="flex justify-between items-center mb-4">
          <h2 class="text-2xl font-bold">Featured Characters</h2>
          <Button
            label="View All"
            icon="pi pi-arrow-right"
            iconPos="right"
            outlined
            @click="router.push('/characters')"
          />
        </div>

        <!-- Characters Grid -->
        <div v-if="characterList.length === 0" class="no-data p-4 text-center">
          <i class="pi pi-info-circle text-4xl text-gray-400 mb-3"></i>
          <p>No characters available at the moment.</p>
        </div>

        <div v-else class="character-grid">
          <Card
            v-for="character in characterList"
            :key="character.id"
            class="character-card"
            @click="goToDetails(character.id)"
          >
            <template #header>
              <div class="character-image">
                <Avatar
                  :image="character.image || ''"
                  size="xlarge"
                  shape="circle"
                  :pt="{
                    image: { class: 'border-4 border-primary shadow-md' },
                  }"
                  alt="Character avatar"
                />
              </div>
            </template>

            <template #title>
              <div class="text-xl font-bold truncate">{{ character.name }}</div>
            </template>

            <template #subtitle>
              <Tag :severity="getStatusSeverity(character.status)" rounded>
                {{ character.status }}
              </Tag>
              <span class="ml-2 text-gray-600">{{ character.species }}</span>
            </template>

            <template #content>
              <div class="character-details">
                <div class="detail-item">
                  <i class="pi pi-map-marker mr-2 text-primary"></i>
                  <span>{{
                    character.location?.name || "Unknown location"
                  }}</span>
                </div>
                <div class="detail-item">
                  <i class="pi pi-home mr-2 text-primary"></i>
                  <span>{{ character.origin?.name || "Unknown origin" }}</span>
                </div>
              </div>
            </template>

            <template #footer>
              <Button
                label="View Details"
                icon="pi pi-user"
                outlined
                class="p-button-sm w-full"
                @click.stop="goToDetails(character.id)"
              />
            </template>
          </Card>
        </div>
      </div>

      <!-- Quick Links Section -->
      <div class="quick-links-section mb-6">
        <h2 class="text-2xl font-bold mb-4">Explore More</h2>
        <div class="quick-links-grid">
          <Card class="quick-link-card">
            <template #header>
              <div class="quick-link-icon">
                <i class="pi pi-map text-4xl text-primary"></i>
              </div>
            </template>
            <template #title>Locations</template>
            <template #content>
              <p>Discover amazing places across dimensions</p>
            </template>
            <template #footer>
              <Button
                label="Explore Locations"
                icon="pi pi-arrow-right"
                iconPos="right"
                @click="router.push('/locations')"
                text
              />
            </template>
          </Card>

          <Card class="quick-link-card">
            <template #header>
              <div class="quick-link-icon">
                <i class="pi pi-video text-4xl text-primary"></i>
              </div>
            </template>
            <template #title>Episodes</template>
            <template #content>
              <p>Browse all episodes and their characters</p>
            </template>
            <template #footer>
              <Button
                label="Browse Episodes"
                icon="pi pi-arrow-right"
                iconPos="right"
                @click="router.push('/episodes')"
                text
              />
            </template>
          </Card>
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped>
.home-container {
  max-width: 1200px;
  margin: 0 auto;
}

.character-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 1.5rem;
}

.character-card {
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.character-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.character-image {
  display: flex;
  justify-content: center;
  padding: 1rem 0;
}

.character-details {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin: 1rem 0;
}

.detail-item {
  display: flex;
  align-items: center;
  font-size: 0.9rem;
}

.quick-links-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 1.5rem;
}

.quick-link-card {
  transition: transform 0.2s;
}

.quick-link-card:hover {
  transform: translateY(-3px);
}

.quick-link-icon {
  display: flex;
  justify-content: center;
  padding: 1.5rem 0;
  background-color: #f8f9fa;
}

@media (max-width: 768px) {
  .character-grid,
  .quick-links-grid {
    grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  }
}

@media (max-width: 480px) {
  .character-grid,
  .quick-links-grid {
    grid-template-columns: 1fr;
  }
}
</style>
