export function toEnrollPayload(form) {
  return {
    athleteId: Number(form.athleteId),
    eventId: Number(form.eventId),
  }
}

export function fromParticipationApi(item) {
  return {
    id: item.id,
    athleteId: item.athleteId,
    athleteName: item.athleteName,
    athleteCountry: item.athleteCountry,
    athleteAge: item.athleteAge,
    athleteSex: item.athleteSex,
    eventId: item.eventId,
    eventName: item.eventName,
    eventCategory: item.eventCategory,
    eventSex: item.eventSex,
    eventFinished: item.eventFinished,
    result: item.result,
    position: item.position,
  }
}
