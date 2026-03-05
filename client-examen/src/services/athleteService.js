import { API_ENDPOINTS, formatEndpoint } from '../config'

export function createAthleteService(httpClient) {
  return {
    async findAll() {
      return httpClient.request(API_ENDPOINTS.athletesGetAll)
    },

    async create(payload) {
      return httpClient.request(API_ENDPOINTS.athletesCreate, {
        method: 'POST',
        body: JSON.stringify(payload),
      })
    },

    async update(id, payload) {
      return httpClient.request(formatEndpoint(API_ENDPOINTS.athletesUpdate, { id }), {
        method: 'PUT',
        body: JSON.stringify(payload),
      })
    },

    async remove(id) {
      return httpClient.request(formatEndpoint(API_ENDPOINTS.athletesDelete, { id }), {
        method: 'DELETE',
      })
    },
  }
}
