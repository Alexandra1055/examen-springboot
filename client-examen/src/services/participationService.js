import { API_ENDPOINTS, formatEndpoint } from '../config'

export function createParticipationService(httpClient) {
  return {
    async findByEvent(eventId) {
      // Aquest endpoint és protegit: enviam token.
      return httpClient.request(formatEndpoint(API_ENDPOINTS.participationsByEvent, { eventId }), {
        auth: true,
      })
    },

    async enroll(payload) {
      // Aquest endpoint és protegit: enviam token.
      return httpClient.request(API_ENDPOINTS.participationsEnroll, {
        auth: true,
        method: 'POST',
        body: JSON.stringify(payload),
      })
    },
  }
}
