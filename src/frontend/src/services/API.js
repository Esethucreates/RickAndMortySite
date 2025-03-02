import axios from "axios";

// Create a base API instance for reuse
const api = axios.create({
  baseURL: "/api",
  timeout: 10000,
  headers: {
    "Content-Type": "application/json",
  },
});

export const characterService = {
  getFeaturedCharacters() {
    return api.get("");
  },

  getAllCharacters(page = 1) {
    return api.get(`/characters?page=${page}`);
  },
  getCharacter(id) {
    return api.get(`/characters/${id}`);
  },
};

export const locationService = {
  getAllLocations(page = 1) {
    return api.get(`/locations?page=${page}`);
  },
  getLocation(id) {
    return api.get(`/locations/${id}`);
  },
};

export const episodeService = {
  getAllEpisodes(page = 1) {
    return api.get(`/episodes?page=${page}`);
  },

  getEpisode(id) {
    return api.get(`/episodes/${id}`);
  },
};

export default api;
