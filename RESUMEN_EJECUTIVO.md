# 🎯 RESUMEN EJECUTIVO - CRUD Wedding RSVP

## 📋 Proyecto Entregado

**Sistema de Confirmación de Asistencia para Boda**
API REST desarrollada con Spring Boot + Java 17

---

## ✅ Funcionalidades Implementadas

### 1. Alta de Invitados ✓
- Registro completo de invitados con todos los campos del formulario
- Validaciones robustas de datos
- Soporte para múltiples adultos por invitado
- Opciones de menú (Carne, Salmón, Vegano)
- Registro de alergias y notas especiales

### 2. Consulta de Invitados ✓
- **Paginación de 10 registros** por página (configurable)
- Filtros por asistencia (asisten / no asisten)
- Búsqueda por ID
- Estadísticas completas
- Ordenamiento por fecha de creación

---

## 🏗️ Arquitectura y Buenas Prácticas

### Patrón de Capas
```
Controller → Service → Repository → Database
     ↓          ↓          ↓
   DTOs    Lógica de   Entidades
           Negocio        JPA
```

### Principios SOLID Aplicados
✅ **Single Responsibility**: Cada clase tiene una única responsabilidad
✅ **Open/Closed**: Extensible sin modificar código existente
✅ **Liskov Substitution**: Uso de interfaces y abstracciones
✅ **Interface Segregation**: Interfaces específicas y cohesivas
✅ **Dependency Inversion**: Inyección de dependencias con Spring

### Otras Buenas Prácticas
✅ DTOs separados para Request y Response
✅ Mappers para conversión Entity-DTO
✅ Validaciones con Bean Validation (@Valid, @NotNull, etc.)
✅ Manejo centralizado de excepciones
✅ Logging con SLF4J
✅ Transacciones con @Transactional
✅ Paginación optimizada con Spring Data
✅ Queries optimizadas (evita N+1)
✅ Configuración externalizada en application.yml
✅ Separación de perfiles (dev/prod)

---

## 📁 Estructura del Código

```
wedding-rsvp/
├── config/              # DataLoader con datos de prueba
├── controller/          # GuestController - REST API
├── exception/           # Excepciones personalizadas + GlobalHandler
├── mapper/              # GuestMapper - Entity ↔ DTO
├── model/
│   ├── dto/            # Request, Response, Page DTOs
│   ├── entity/         # Guest, Adult (JPA Entities)
│   └── enums/          # MenuType
├── repository/         # GuestRepository, AdultRepository
├── service/            # GuestService + Implementation
└── WeddingRsvpApplication.java
```

---

## 🔧 Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 17 | Lenguaje de programación |
| Spring Boot | 3.2.0 | Framework principal |
| Spring Data JPA | 3.2.0 | Persistencia de datos |
| H2 Database | Runtime | Base de datos (desarrollo) |
| MySQL | 8+ | Base de datos (producción) |
| Lombok | Latest | Reducir boilerplate |
| Bean Validation | 3.0 | Validaciones |
| Maven | 3.6+ | Gestión de dependencias |

---

## 📡 Endpoints Disponibles

### Operaciones CRUD

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| **POST** | `/api/v1/guests` | ✅ **Crear invitado (ALTA)** |
| **GET** | `/api/v1/guests/{id}` | ✅ **Consultar por ID** |
| **GET** | `/api/v1/guests?page=0&size=10` | ✅ **Listar todos (PAGINADO)** |
| **GET** | `/api/v1/guests/attending` | ✅ Filtrar los que asisten |
| **GET** | `/api/v1/guests/not-attending` | ✅ Filtrar los que NO asisten |
| **GET** | `/api/v1/guests/stats` | ✅ Estadísticas |
| **GET** | `/api/v1/guests/health` | ✅ Health check |

---

## 📊 Modelo de Datos

### Guest (Invitado)
- id (Long, auto-generado)
- attending (Boolean) - ¿Asiste?
- numberOfAdults (Integer)
- numberOfChildren (Integer)
- contactEmail (String, opcional)
- contactPhone (String, opcional)
- notes (String, opcional)
- createdAt (LocalDateTime)
- updatedAt (LocalDateTime)
- **Relación:** One-to-Many con Adult

### Adult (Adulto)
- id (Long, auto-generado)
- fullName (String) - Nombre completo
- allergies (String, opcional)
- menu (MenuType) - CARNE | SALMON | VEGANO
- adultOrder (Integer) - Orden en la lista
- **Relación:** Many-to-One con Guest

---

## ✅ Validaciones Implementadas

### Validaciones de Datos
- Nombre: 2-200 caracteres, obligatorio
- Email: Formato válido, opcional
- Teléfono: 10 dígitos, opcional
- Número de adultos: 0-20
- Número de niños: 0-20
- Alergias: Máximo 500 caracteres
- Notas: Máximo 500 caracteres

### Validaciones de Negocio
- El número de adultos debe coincidir con la lista
- Si asiste, debe tener al menos 1 adulto
- Paginación: página >= 0, tamaño 1-100

---

## 🚀 Cómo Ejecutar

### Opción 1: Maven
```bash
cd wedding-rsvp
mvn clean install
mvn spring-boot:run
```

### Opción 2: JAR
```bash
mvn clean package
java -jar target/rsvp-service-1.0.0.jar
```

### Opción 3: IDE
1. Importar proyecto en IntelliJ/Eclipse
2. Run `WeddingRsvpApplication.java`

**URL Base:** http://localhost:8080/api

---

## 📝 Ejemplo de Uso

### Crear Invitado
```bash
curl -X POST http://localhost:8080/api/v1/guests \
  -H "Content-Type: application/json" \
  -d '{
    "attending": true,
    "numberOfAdults": 2,
    "numberOfChildren": 1,
    "adults": [
      {
        "fullName": "Emmanuel Pakal",
        "allergies": "Ninguna",
        "menu": "CARNE",
        "adultOrder": 1
      },
      {
        "fullName": "Naydelin López",
        "allergies": "Alérgica a mariscos",
        "menu": "VEGANO",
        "adultOrder": 2
      }
    ],
    "contactEmail": "emmanuel@example.com",
    "contactPhone": "5551234567",
    "notes": "Mesa cerca de la familia"
  }'
```

### Listar Invitados (Paginado)
```bash
curl http://localhost:8080/api/v1/guests?page=0&size=10
```

---

## 📦 Archivos Entregados

```
wedding-rsvp/
├── src/                          # Código fuente completo
├── pom.xml                       # Dependencias Maven
├── README.md                     # Documentación completa
├── GUIA_PASO_A_PASO.md          # Tutorial detallado
├── POSTMAN_EXAMPLES.md          # Ejemplos de Postman
└── .gitignore                    # Archivos a excluir
```

---

## 🎯 Características Destacadas

✅ **Código Limpio**: Siguiendo principios SOLID y Clean Code
✅ **Arquitectura en Capas**: Separación clara de responsabilidades
✅ **Validaciones Robustas**: Datos seguros y consistentes
✅ **Manejo de Errores**: Respuestas de error claras y útiles
✅ **Paginación Eficiente**: Optimizado para grandes volúmenes
✅ **Logging**: Trazabilidad completa de operaciones
✅ **Documentación**: README completo + Guía paso a paso
✅ **Datos de Prueba**: 3 invitados precargados para testing
✅ **Consola H2**: Acceso directo a la base de datos
✅ **Listo para Producción**: Perfiles dev/prod configurados

---

## 🔜 Mejoras Futuras Sugeridas

- [ ] Actualizar invitado (PUT /api/v1/guests/{id})
- [ ] Eliminar invitado (DELETE /api/v1/guests/{id})
- [ ] Búsqueda por nombre
- [ ] Exportar a Excel/PDF
- [ ] Autenticación JWT
- [ ] Tests unitarios e integración
- [ ] Documentación Swagger/OpenAPI
- [ ] Deploy en cloud (AWS/Heroku)
- [ ] Envío de emails de confirmación
- [ ] Dashboard administrativo

---

## 📞 Soporte

Para cualquier duda o problema:
1. Revisa la **GUIA_PASO_A_PASO.md**
2. Consulta el **README.md**
3. Revisa los logs de la aplicación
4. Verifica la consola H2: http://localhost:8080/api/h2-console

---

## ✨ Estado del Proyecto

**✅ COMPLETADO Y LISTO PARA USAR**

- Alta de invitados: ✅ Implementado
- Consulta con paginación: ✅ Implementado (10 por página)
- Validaciones: ✅ Implementadas
- Buenas prácticas: ✅ Aplicadas
- Documentación: ✅ Completa
- Datos de prueba: ✅ Incluidos
- Listo para producción: ✅ Sí (requiere configurar MySQL)

---

**Desarrollado para:** Emmanuel Pakal & Naydelin López
**Fecha:** Enero 2025
**Tecnología:** Spring Boot 3.2.0 + Java 17
**Estado:** ✅ Producción Ready
