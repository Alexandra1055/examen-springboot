import { API_ENDPOINTS, formatEndpoint } from '../config'

export function createEventService(httpClient) {
  return {
    async findAll() {
      return httpClient.request(API_ENDPOINTS.eventsGetAll)
    },

    async create(payload) {
      return httpClient.request(API_ENDPOINTS.eventsCreate, {
        method: 'POST',
        body: JSON.stringify(payload),
      })
    },

    async update(id, payload) {
      return httpClient.request(formatEndpoint(API_ENDPOINTS.eventsUpdate, { id }), {
        method: 'PUT',
        body: JSON.stringify(payload),
      })
    },

    async remove(id) {
      return httpClient.request(formatEndpoint(API_ENDPOINTS.eventsDelete, { id }), {
        method: 'DELETE',
      })
    },
  }
}
