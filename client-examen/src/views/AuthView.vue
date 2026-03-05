<script setup>
import { reactive } from 'vue'
import { useApi } from '../composables/useApi'

const { state, isLogged, login, logout } = useApi()

const form = reactive({
  email: state.authEmail,
  password: state.authPassword,
})

async function submit() {
  await login(form.email, form.password)
}
</script>

<template>
  <section class="panel">
    <h2>Autenticació JWT</h2>
    <form @submit.prevent="submit">
      <label>
        Correu electrònic
        <input v-model="form.email" type="email" required />
      </label>
      <label>
        Contrasenya
        <input v-model="form.password" type="password" required />
      </label>
      <div class="actions">
        <button :disabled="state.loading" type="submit">Entrar</button>
        <button :disabled="state.loading || !isLogged" class="ghost" type="button" @click="logout">Sortir</button>
      </div>
    </form>
    <p v-if="isLogged" class="ok">Token carregat.</p>
  </section>
</template>

<style scoped>
.panel {
  border: 1px solid #d8e1e8;
  border-radius: 16px;
  padding: 1rem;
  background: #fff;
}

form {
  display: grid;
  gap: 0.7rem;
}

label {
  display: grid;
  gap: 0.35rem;
}

input, button {
  font: inherit;
  border-radius: 10px;
}

input {
  border: 1px solid #ccd7e0;
  background: #fff;
  color: #22303c;
  padding: 0.55rem 0.7rem;
}

.actions {
  display: flex;
  gap: 0.6rem;
}

button {
  border: 1px solid #007f5f;
  background: #007f5f;
  color: #fff;
  padding: 0.55rem 0.8rem;
  font-weight: 700;
}

button.ghost {
  background: transparent;
  border-color: #96a5b3;
  color: #3f4f5e;
}

.ok {
  color: #0c6d57;
}
</style>
