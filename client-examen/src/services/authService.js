import { API_ENDPOINTS } from '../config'

export function createAuthService(httpClient) {
  return {
    async login(email, password) {
      return httpClient.request(API_ENDPOINTS.login, {
        method: 'POST',
        body: JSON.stringify({ email, password }),
      })
    },
  }
}
