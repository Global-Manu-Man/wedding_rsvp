# Colección Postman - Wedding RSVP API

## Variables de entorno
```
base_url = http://localhost:8080/api
```

## 1. Health Check
```
GET {{base_url}}/v1/guests/health
```

## 2. Crear Invitado que ASISTE
```
POST {{base_url}}/v1/guests
Content-Type: application/json

{
  "attending": true,
  "numberOfAdults": 2,
  "numberOfChildren": 1,
  "adults": [
    {
      "fullName": "Emmanuel Pakal Cruz",
      "allergies": "Ninguna",
      "menu": "CARNE",
      "adultOrder": 1
    },
    {
      "fullName": "Naydelin López Hernández",
      "allergies": "Alérgica a los mariscos",
      "menu": "VEGANO",
      "adultOrder": 2
    }
  ],
  "contactEmail": "emmanuel.pakal@example.com",
  "contactPhone": "5551234567",
  "notes": "Mesa cerca de la pista de baile"
}
```

## 3. Crear Invitado que NO ASISTE
```
POST {{base_url}}/v1/guests
Content-Type: application/json

{
  "attending": false,
  "numberOfAdults": 1,
  "numberOfChildren": 0,
  "adults": [
    {
      "fullName": "Carlos Rodríguez",
      "menu": "CARNE",
      "adultOrder": 1
    }
  ],
  "contactEmail": "carlos@example.com",
  "contactPhone": "5559876543",
  "notes": "No puedo asistir por viaje de trabajo"
}
```

## 4. Obtener Invitado por ID
```
GET {{base_url}}/v1/guests/1
```

## 5. Obtener Todos los Invitados (Primera Página)
```
GET {{base_url}}/v1/guests?page=0&size=10
```

## 6. Obtener Todos los Invitados (Segunda Página)
```
GET {{base_url}}/v1/guests?page=1&size=10
```

## 7. Obtener Solo Invitados que ASISTEN
```
GET {{base_url}}/v1/guests/attending?page=0&size=10
```

## 8. Obtener Solo Invitados que NO ASISTEN
```
GET {{base_url}}/v1/guests/not-attending?page=0&size=10
```

## 9. Obtener Estadísticas
```
GET {{base_url}}/v1/guests/stats
```

## Ejemplos de Errores de Validación

### Error: Número de adultos no coincide
```
POST {{base_url}}/v1/guests
Content-Type: application/json

{
  "attending": true,
  "numberOfAdults": 2,
  "numberOfChildren": 0,
  "adults": [
    {
      "fullName": "Solo Un Adulto",
      "menu": "CARNE",
      "adultOrder": 1
    }
  ],
  "contactEmail": "test@example.com"
}

# Respuesta esperada: 400 Bad Request
# "El número de adultos (2) no coincide con la cantidad de adultos en la lista (1)"
```

### Error: Asiste sin adultos
```
POST {{base_url}}/v1/guests
Content-Type: application/json

{
  "attending": true,
  "numberOfAdults": 0,
  "numberOfChildren": 0,
  "adults": [],
  "contactEmail": "test@example.com"
}

# Respuesta esperada: 400 Bad Request
# "Si confirma asistencia, debe registrar al menos un adulto"
```

### Error: Validación de campos
```
POST {{base_url}}/v1/guests
Content-Type: application/json

{
  "attending": true,
  "numberOfAdults": 1,
  "numberOfChildren": 0,
  "adults": [
    {
      "fullName": "A",
      "allergies": "...[más de 500 caracteres]...",
      "adultOrder": 1
    }
  ],
  "contactEmail": "email-invalido",
  "contactPhone": "123"
}

# Respuesta esperada: 400 Bad Request con múltiples errores de validación
```

## Notas

- Todos los endpoints requieren Content-Type: application/json
- La paginación es 0-indexed (primera página = 0)
- El tamaño máximo de página es 100
- Los campos de email y teléfono son opcionales
- Los tipos de menú válidos son: CARNE, SALMON, VEGANO
