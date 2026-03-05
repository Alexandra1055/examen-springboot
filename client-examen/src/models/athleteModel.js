export function toAthletePayload(form) {
  return {
    name: form.name,
    country: form.country,
    age: Number(form.age),
    sex: form.sex,
  }
}

export function fromAthleteApi(item) {
  return {
    id: item.id,
    name: item.name,
    country: item.country,
    age: item.age,
    sex: item.sex,
  }
}
