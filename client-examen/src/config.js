const env = import.meta.env

export const API_ENDPOINTS = {
  login: env.VITE_API_LOGIN_ENDPOINT,

  athletesGetAll: env.VITE_API_ATHLETES_GET_ALL,
  athletesCreate: env.VITE_API_ATHLETES_CREATE,
  athletesUpdate: env.VITE_API_ATHLETES_UPDATE,
  athletesDelete: env.VITE_API_ATHLETES_DELETE,

  eventsGetAll: env.VITE_API_EVENTS_GET_ALL,
  eventsCreate: env.VITE_API_EVENTS_CREATE,
  eventsUpdate: env.VITE_API_EVENTS_UPDATE,
  eventsDelete: env.VITE_API_EVENTS_DELETE,

  participationsByEvent: env.VITE_API_PARTICIPATIONS_BY_EVENT,
  participationsEnroll: env.VITE_API_PARTICIPATIONS_ENROLL,
}

export function formatEndpoint(template, params = {}) {
  let output = template
  Object.entries(params).forEach(([key, value]) => {
    output = output.replaceAll(`{${key}}`, String(value))
  })
  return output
}

export function assertEndpointsConfigured() {
  const missing = Object.entries(API_ENDPOINTS)
    .filter(([, value]) => !value)
    .map(([key]) => key)

  if (missing.length > 0) {
    throw new Error(`Falten variables d'entorn de Vite: ${missing.join(', ')}`)
  }
}
