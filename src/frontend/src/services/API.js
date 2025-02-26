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
  getAllCharacters(page = 0) {
    return api.get(`/characters?page=${page}`);
  },
  getCharacter(id) {
    return api.get(`/characters/${id}`);
  },
};

export const locationService = {
  getAllLocations(page = 0) {
    return api.get(`/locations?page=${page}`);
  },
  getLocation(id) {
    return api.get(`/locations/${id}`);
  },
};

export default api;
