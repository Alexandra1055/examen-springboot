<script setup>
import { onMounted, reactive } from 'vue'
import { useApi } from '../composables/useApi'

const sexes = ['MALE', 'FEMALE']
const sexLabels = {
  MALE: 'Masculí',
  FEMALE: 'Femení',
}

const { state, loadAthletes, createAthlete, updateAthlete, deleteAthlete } = useApi()

const form = reactive({
  id: null,
  name: '',
  country: '',
  age: 18,
  sex: 'MALE',
})

function clearForm() {
  form.id = null
  form.name = ''
  form.country = ''
  form.age = 18
  form.sex = 'MALE'
}

function editItem(item) {
  form.id = item.id
  form.name = item.name
  form.country = item.country
  form.age = item.age
  form.sex = item.sex
}

async function submit() {
  const payload = {
    name: form.name,
    country: form.country,
    age: Number(form.age),
    sex: form.sex,
  }

  if (form.id) {
    await updateAthlete(form.id, payload)
  } else {
    await createAthlete(payload)
  }

  clearForm()
}

onMounted(loadAthletes)
</script>

<template>
  <section class="panel">
    <h2>Atletes</h2>

    <form class="grid" @submit.prevent="submit">
      <input v-model="form.name" placeholder="Nom" required />
      <input v-model="form.country" placeholder="País" required />
      <input v-model.number="form.age" type="number" min="12" max="80" required />
      <select v-model="form.sex">
        <option v-for="sex in sexes" :key="sex" :value="sex">{{ sexLabels[sex] }}</option>
      </select>
      <button :disabled="state.loading" type="submit">{{ form.id ? 'Actualitzar' : 'Crear' }}</button>
      <button :disabled="state.loading" class="ghost" type="button" @click="clearForm">Netejar</button>
    </form>

    <table>
      <thead>
        <tr><th>ID</th><th>Nom</th><th>País</th><th>Edat</th><th>Sexe</th><th>Accions</th></tr>
      </thead>
      <tbody>
        <tr v-for="athlete in state.athletes" :key="athlete.id">
          <td>{{ athlete.id }}</td>
          <td>{{ athlete.name }}</td>
          <td>{{ athlete.country }}</td>
          <td>{{ athlete.age }}</td>
          <td>{{ sexLabels[athlete.sex] }}</td>
          <td class="actions">
            <button class="ghost" @click="editItem(athlete)">Editar</button>
            <button class="danger" @click="deleteAthlete(athlete.id)">Eliminar</button>
          </td>
        </tr>
      </tbody>
    </table>
  </section>
</template>

<style scoped>
.panel {
  border: 1px solid #d8e1e8;
  border-radius: 16px;
  padding: 1rem;
  background: #fff;
}

.grid {
  display: grid;
  gap: 0.5rem;
  grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
  margin-bottom: 1rem;
}

input, select, button {
  font: inherit;
  border-radius: 10px;
}

input, select {
  border: 1px solid #ccd7e0;
  background: #fff;
  color: #22303c;
  padding: 0.55rem 0.7rem;
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

button.danger {
  border-color: #d64550;
  background: #d64550;
  color: #fff;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border-bottom: 1px solid #e4ebf1;
  padding: 0.45rem;
  text-align: left;
  color: #22303c;
}

th {
  color: #556371;
}

.actions {
  display: flex;
  gap: 0.4rem;
}
</style>
