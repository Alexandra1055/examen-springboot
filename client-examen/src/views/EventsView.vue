<script setup>
import { onMounted, reactive } from 'vue'
import { useApi } from '../composables/useApi'

const sexes = ['MALE', 'FEMALE']
const sexLabels = {
  MALE: 'Masculí',
  FEMALE: 'Femení',
}

const categories = ['ENDURANCE', 'SPRINT', 'JUMP', 'THROW']
const categoryLabels = {
  ENDURANCE: 'Fons',
  SPRINT: 'Velocitat',
  JUMP: 'Salt',
  THROW: 'Llançament',
}

const { state, loadEvents, createEvent, updateEvent, deleteEvent } = useApi()

const form = reactive({
  id: null,
  name: '',
  category: 'SPRINT',
  sex: 'MALE',
  finished: false,
})

function clearForm() {
  form.id = null
  form.name = ''
  form.category = 'SPRINT'
  form.sex = 'MALE'
  form.finished = false
}

function editItem(item) {
  form.id = item.id
  form.name = item.name
  form.category = item.category
  form.sex = item.sex
  form.finished = item.finished
}

async function submit() {
  const payload = {
    name: form.name,
    category: form.category,
    sex: form.sex,
    finished: Boolean(form.finished),
  }

  if (form.id) {
    await updateEvent(form.id, payload)
  } else {
    await createEvent(payload)
  }

  clearForm()
}

onMounted(loadEvents)
</script>

<template>
  <section class="panel">
    <h2>Proves</h2>

    <form class="grid" @submit.prevent="submit">
      <input v-model="form.name" placeholder="Nom" required />
      <select v-model="form.category">
        <option v-for="category in categories" :key="category" :value="category">{{ categoryLabels[category] }}</option>
      </select>
      <select v-model="form.sex">
        <option v-for="sex in sexes" :key="sex" :value="sex">{{ sexLabels[sex] }}</option>
      </select>
      <label class="check"><input v-model="form.finished" type="checkbox" /> Finalitzada</label>
      <button :disabled="state.loading" type="submit">{{ form.id ? 'Actualitzar' : 'Crear' }}</button>
      <button :disabled="state.loading" class="ghost" type="button" @click="clearForm">Netejar</button>
    </form>

    <table>
      <thead>
        <tr><th>ID</th><th>Nom</th><th>Categoria</th><th>Sexe</th><th>Finalitzada</th><th>Accions</th></tr>
      </thead>
      <tbody>
        <tr v-for="event in state.events" :key="event.id">
          <td>{{ event.id }}</td>
          <td>{{ event.name }}</td>
          <td>{{ categoryLabels[event.category] }}</td>
          <td>{{ sexLabels[event.sex] }}</td>
          <td>{{ event.finished ? 'Sí' : 'No' }}</td>
          <td class="actions">
            <button class="ghost" @click="editItem(event)">Editar</button>
            <button class="danger" @click="deleteEvent(event.id)">Eliminar</button>
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

.check {
  display: flex;
  align-items: center;
  gap: 0.4rem;
  padding-left: 0.2rem;
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
