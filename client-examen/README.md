# client-examen

Frontend Vue per provar l'API REST de l'examen de Spring Boot.

## Configuracio

1. Copia `.env.example` a `.env`.
2. Ajusta tots els endpoints perquè apuntin a la API dels alumnes.
3. Arranca el projecte.

## Comandes

```bash
npm install
npm run dev
```

## Notes

- L'endpoint d'inscripcio d'atletes a proves necessita JWT.
- Aquest client envia el token `Bearer` automaticament després de fer login.
