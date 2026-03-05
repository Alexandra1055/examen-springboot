export function toEventPayload(form) {
  return {
    name: form.name,
    category: form.category,
    sex: form.sex,
    finished: Boolean(form.finished),
  }
}

export function fromEventApi(item) {
  return {
    id: item.id,
    name: item.name,
    category: item.category,
    sex: item.sex,
    finished: item.finished,
  }
}
