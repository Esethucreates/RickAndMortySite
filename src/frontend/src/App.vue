<script setup>
import { RouterLink, RouterView } from "vue-router";
</script>
<template>
  <div class="app">
    <header>
      <nav>
        <div class="logo">
          <RouterLink to="/">
            <h1>Rick and Morty Explorer</h1>
          </RouterLink>
        </div>
        <div class="nav-links">
          <RouterLink to="/characters">Characters</RouterLink>
          <RouterLink to="/locations">Locations</RouterLink>
          <RouterLink to="/episodes">Episodes</RouterLink>
        </div>
      </nav>
    </header>
    <main>
      <RouterView />
    </main>
    <footer>
      <p>&copy; {{ new Date().getFullYear() }} Rick and Morty Explorer</p>
    </footer>
  </div>
</template>

<style>
.app {
  font-family: "Arial", sans-serif;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

header {
  padding: 20px 0;
  border-bottom: 1px solid #eee;
}

nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo h1 {
  margin: 0;
  font-size: 1.5rem;
  color: #333;
}

.nav-links {
  display: flex;
  gap: 20px;
}

.nav-links a {
  text-decoration: none;
  color: #333;
  padding: 5px 10px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.nav-links a:hover,
.nav-links a.router-link-active {
  background-color: #f0f0f0;
  color: #42b883; /* Vue green */
}

main {
  padding: 20px 0;
  min-height: calc(100vh - 200px);
}

footer {
  padding: 20px 0;
  text-align: center;
  border-top: 1px solid #eee;
  color: #666;
}
</style>
