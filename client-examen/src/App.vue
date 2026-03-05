<script setup>
import { onMounted } from 'vue'
import { RouterLink, RouterView } from 'vue-router'
import { useApi } from './composables/useApi'

const { state, ensureConfig, setMessage } = useApi()

onMounted(() => {
  try {
    ensureConfig()
  } catch (error) {
    setMessage('error', error.message)
  }
})
</script>

<template>
  <main class="layout">
    <header class="navbar">
      <div class="brand">Sports</div>
      <nav>
        <RouterLink to="/auth">Accés</RouterLink>
        <RouterLink to="/athletes">Atletes</RouterLink>
        <RouterLink to="/events">Proves</RouterLink>
        <RouterLink to="/participations">Participacions</RouterLink>
      </nav>
    </header>

    <RouterView />

    <p v-if="state.error" class="msg error">{{ state.error }}</p>
    <p v-if="state.info" class="msg info">{{ state.info }}</p>
  </main>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Archivo+Black&family=IBM+Plex+Sans:wght@400;500;700&display=swap');

:global(body) {
  margin: 0;
  font-family: 'IBM Plex Sans', sans-serif;
  background:
    radial-gradient(circle at 8% 12%, rgba(0, 175, 125, 0.08), transparent 36%),
    radial-gradient(circle at 90% 6%, rgba(255, 99, 71, 0.09), transparent 34%),
    #f4f7fa;
  color: #1f2d3a;
}

.layout {
  max-width: 1220px;
  margin: 0 auto;
  padding: 1rem;
  display: grid;
  gap: 1rem;
}

.navbar {
  border: 1px solid #d8e0e7;
  border-radius: 16px;
  background: linear-gradient(145deg, #ffffff, #f5f8fb);
  padding: 0.9rem 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 1rem;
  flex-wrap: wrap;
}

.brand {
  font-family: 'Archivo Black', sans-serif;
  color: #0c6d57;
  letter-spacing: 0.03em;
}

nav {
  display: flex;
  gap: 0.5rem;
  flex-wrap: wrap;
}

a {
  color: #334155;
  text-decoration: none;
  border: 1px solid #d3dde5;
  border-radius: 999px;
  padding: 0.35rem 0.75rem;
  background: #fff;
}

a.router-link-exact-active {
  background: #007f5f;
  color: #fff;
  border-color: #007f5f;
  font-weight: 700;
}

.msg {
  border-radius: 10px;
  padding: 0.65rem 0.8rem;
  font-weight: 600;
}

.msg.error {
  background: rgba(230, 57, 70, 0.12);
  border: 1px solid rgba(230, 57, 70, 0.3);
  color: #8f1d28;
}

.msg.info {
  background: rgba(0, 127, 95, 0.12);
  border: 1px solid rgba(0, 127, 95, 0.28);
  color: #0b5e49;
}

@media (max-width: 700px) {
  .layout {
    padding: 0.6rem;
  }
}
</style>
