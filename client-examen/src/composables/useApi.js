import { computed, reactive } from 'vue'
import { assertEndpointsConfigured } from '../config'
import { createHttpClient } from '../api/httpClient'
import { createAuthService } from '../services/authService'
import { createAthleteService } from '../services/athleteService'
import { createEventService } from '../services/eventService'
import { createParticipationService } from '../services/participationService'
import { fromAthleteApi, toAthletePayload } from '../models/athleteModel'
import { fromEventApi, toEventPayload } from '../models/eventModel'
import { fromParticipationApi, toEnrollPayload } from '../models/participationModel'

const state = reactive({
  loading: false,
  error: '',
  info: '',
  token: '',
  // Credencials per defecte dels usuaris seed del backend.
  authEmail: 'admin@sports.dev',
  authPassword: '1234',
  athletes: [],
  events: [],
  participations: [],
})

let checked = false

const httpClient = createHttpClient({ getToken: () => state.token })
const authService = createAuthService(httpClient)
const athleteService = createAthleteService(httpClient)
const eventService = createEventService(httpClient)
const participationService = createParticipationService(httpClient)

function setMessage(type, message) {
  if (type === 'error') {
    state.error = message
    state.info = ''
    return
  }

  state.info = message
  state.error = ''
}

function ensureConfig() {
  if (checked) return
  assertEndpointsConfigured()
  checked = true
}

async function guarded(action) {
  // Centralitzam la gestió d'estat de càrrega i errors de la UI.
  state.loading = true
  try {
    return await action()
  } catch (error) {
    setMessage('error', error.message)
    return null
  } finally {
    state.loading = false
  }
}

async function login(email = state.authEmail, password = state.authPassword) {
  ensureConfig()
  return guarded(async () => {
    const data = await authService.login(email, password)
    state.token = data.token
    state.authEmail = email
    state.authPassword = password
    setMessage('info', 'Sessio iniciada correctament')
    return data
  })
}

function logout() {
  state.token = ''
  setMessage('info', 'Sessio tancada')
}

async function loadAthletes() {
  ensureConfig()
  return guarded(async () => {
    const data = await athleteService.findAll()
    state.athletes = data.map(fromAthleteApi)
    return state.athletes
  })
}

async function createAthlete(formPayload) {
  ensureConfig()
  return guarded(async () => {
    await athleteService.create(toAthletePayload(formPayload))
    setMessage('info', 'Atleta creat')
    return loadAthletes()
  })
}

async function updateAthlete(id, formPayload) {
  ensureConfig()
  return guarded(async () => {
    await athleteService.update(id, toAthletePayload(formPayload))
    setMessage('info', 'Atleta actualitzat')
    return loadAthletes()
  })
}

async function deleteAthlete(id) {
  ensureConfig()
  return guarded(async () => {
    await athleteService.remove(id)
    setMessage('info', 'Atleta eliminat')
    return loadAthletes()
  })
}

async function loadEvents() {
  ensureConfig()
  return guarded(async () => {
    const data = await eventService.findAll()
    state.events = data.map(fromEventApi)
    return state.events
  })
}

async function createEvent(formPayload) {
  ensureConfig()
  return guarded(async () => {
    await eventService.create(toEventPayload(formPayload))
    setMessage('info', 'Prova creada')
    return loadEvents()
  })
}

async function updateEvent(id, formPayload) {
  ensureConfig()
  return guarded(async () => {
    await eventService.update(id, toEventPayload(formPayload))
    setMessage('info', 'Prova actualitzada')
    return loadEvents()
  })
}

async function deleteEvent(id) {
  ensureConfig()
  return guarded(async () => {
    await eventService.remove(id)
    setMessage('info', 'Prova eliminada')
    return loadEvents()
  })
}

async function loadParticipationsByEvent(eventId) {
  ensureConfig()
  if (!eventId) {
    state.participations = []
    return []
  }

  return guarded(async () => {
    const data = await participationService.findByEvent(eventId)
    state.participations = data.map(fromParticipationApi)
    return state.participations
  })
}

async function enrollWithoutResult(formPayload) {
  ensureConfig()
  return guarded(async () => {
    const data = await participationService.enroll(toEnrollPayload(formPayload))
    setMessage('info', 'Inscripcio creada')
    return fromParticipationApi(data)
  })
}

const isLogged = computed(() => !!state.token)

export function useApi() {
  return {
    state,
    isLogged,
    ensureConfig,
    setMessage,
    login,
    logout,
    loadAthletes,
    createAthlete,
    updateAthlete,
    deleteAthlete,
    loadEvents,
    createEvent,
    updateEvent,
    deleteEvent,
    loadParticipationsByEvent,
    enrollWithoutResult,
  }
}
