export function createHttpClient({ getToken }) {
  function extractDetailFromRaw(raw) {
    if (!raw) return null
    const match = raw.match(/"detail"\s*:\s*"([^"]*)"/)
    return match ? match[1] : null
  }

  async function request(url, options = {}) {
    const { auth = false, ...fetchOptions } = options

    const headers = {
      'Content-Type': 'application/json',
      ...(fetchOptions.headers || {}),
    }

    // Només enviam Bearer als endpoints que ho requereixen.
    const token = auth ? getToken?.() : null
    if (auth && token) {
      headers.Authorization = `Bearer ${token}`
    }

    const response = await fetch(url, { ...fetchOptions, headers })
    const raw = await response.text()
    const contentType = response.headers.get('content-type') || ''

    let data = null
    if (raw) {
      if (contentType.includes('application/json')) {
        try {
          data = JSON.parse(raw)
        } catch {
          // Evitam rompre la UI si el backend retorna JSON incomplet.
          data = { error: raw }
        }
      } else {
        data = { error: raw }
      }
    }

    if (!response.ok) {
      const detailFromRaw = extractDetailFromRaw(raw)
      const message = data?.detail || detailFromRaw || data?.error || raw || response.statusText || `HTTP ${response.status}`
      throw new Error(message)
    }

    return data
  }

  return { request }
}
