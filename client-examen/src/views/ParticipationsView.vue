<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { useApi } from '../composables/useApi'

const sexLabels = {
  MALE: 'Masculí',
  FEMALE: 'Femení',
}

const { state, isLogged, loadAthletes, loadEvents, loadParticipationsByEvent, enrollWithoutResult } = useApi()

const selectedEventId = ref('')

const enrollForm = reactive({
  athleteId: '',
  eventId: '',
})

const availableEventsForEnroll = computed(() =>
  state.events.filter((event) => !event.finished),
)
const selectedEvent = computed(() =>
  state.events.find((event) => String(event.id) === String(selectedEventId.value)) || null,
)

async function refreshParticipations() {
  await loadParticipationsByEvent(selectedEventId.value)
}

async function submitEnroll() {
  await enrollWithoutResult({
    athleteId: Number(enrollForm.athleteId),
    eventId: Number(enrollForm.eventId),
  })

  if (selectedEventId.value && Number(selectedEventId.value) === Number(enrollForm.eventId)) {
    await refreshParticipations()
  }
}

onMounted(async () => {
  await Promise.all([loadAthletes(), loadEvents()])
})
</script>

<template>
  <section class="panel">
    <h2>Participacions</h2>

    <form class="enroll" @submit.prevent="submitEnroll">
      <h3>Inscriure atleta</h3>
      <select v-model="enrollForm.athleteId" required>
        <option value="">Selecciona un atleta</option>
        <option v-for="athlete in state.athletes" :key="athlete.id" :value="athlete.id">
          #{{ athlete.id }} {{ athlete.name }} ({{ sexLabels[athlete.sex] }})
        </option>
      </select>
      <select v-model="enrollForm.eventId" required>
        <option value="">Selecciona una prova no finalitzada</option>
        <option v-for="event in availableEventsForEnroll" :key="event.id" :value="event.id">
          #{{ event.id }} {{ event.name }} · {{ sexLabels[event.sex] }}
        </option>
      </select>
      <button :disabled="!isLogged || state.loading">Inscriure</button>
      <p class="hint">Només es permet si la prova no ha finalitzat.</p>
    </form>

    <label class="selector">
      Prova per llistar participacions
      <select v-model="selectedEventId" @change="refreshParticipations">
        <option value="">Selecciona una prova</option>
        <option v-for="event in state.events" :key="event.id" :value="event.id">
          #{{ event.id }} {{ event.name }} · {{ sexLabels[event.sex] }}
        </option>
      </select>
    </label>

    <p v-if="selectedEvent" class="event-status">
      Estat de la prova:
      <strong>{{ selectedEvent.finished ? 'Finalitzada' : 'No finalitzada' }}</strong>
    </p>

    <table>
      <thead>
        <tr>
          <th>Nom atleta</th>
          <th>Resultat</th>
          <th>Posició</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in state.participations" :key="item.id">
          <td>{{ item.athleteName }}</td>
          <td>{{ item.result || '-' }}</td>
          <td>{{ item.position || '-' }}</td>
        </tr>
      </tbody>
    </table>
  </section>
</template>

<style scoped>
.panel {
  border: 1px solid #d9e0e7;
  border-radius: 16px;
  padding: 1rem;
  background: #ffffff;
}

.selector {
  display: grid;
  gap: 0.35rem;
  margin-bottom: 0.8rem;
}

.enroll {
  display: grid;
  gap: 0.45rem;
  border: 1px solid #dfe6ec;
  border-radius: 12px;
  padding: 0.7rem;
  margin-bottom: 1rem;
  background: #f8fafc;
}

input, select, button {
  font: inherit;
  border-radius: 10px;
}

input, select {
  border: 1px solid #ccd5de;
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

.hint {
  margin: 0;
  color: #5b6975;
  font-size: 0.9rem;
}

.event-status {
  margin: 0 0 0.7rem 0;
  color: #465564;
}

table {
  width: 100%;
  border-collapse: collapse;
  display: block;
  overflow-x: auto;
  white-space: nowrap;
}

th, td {
  border-bottom: 1px solid #e5ebf0;
  padding: 0.45rem;
  text-align: left;
  color: #22303c;
}

th {
  color: #556371;
}
</style>
