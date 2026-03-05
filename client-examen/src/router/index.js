import { createRouter, createWebHistory } from 'vue-router'
import AuthView from '../views/AuthView.vue'
import AthletesView from '../views/AthletesView.vue'
import EventsView from '../views/EventsView.vue'
import ParticipationsView from '../views/ParticipationsView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'athletes-home', component: AthletesView },
    { path: '/auth', name: 'auth', component: AuthView },
    { path: '/athletes', name: 'athletes', component: AthletesView },
    { path: '/events', name: 'events', component: EventsView },
    { path: '/participations', name: 'participations', component: ParticipationsView },
  ],
})

export default router
