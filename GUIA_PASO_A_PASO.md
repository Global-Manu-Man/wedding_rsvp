# 📖 Guía Paso a Paso - Wedding RSVP CRUD

## ✅ Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

1. **Java Development Kit (JDK) 17 o superior**
   - Verificar: `java -version`
   - Descargar: https://www.oracle.com/java/technologies/downloads/

2. **Maven 3.6 o superior**
   - Verificar: `mvn -version`
   - Descargar: https://maven.apache.org/download.cgi

3. **IDE (Recomendado)**
   - IntelliJ IDEA (recomendado)
   - Eclipse
   - VS Code con extensiones de Java

4. **Postman o Thunder Client** (para probar la API)
   - Descargar Postman: https://www.postman.com/downloads/

## 🚀 Paso 1: Obtener el Proyecto

```bash
# Si tienes el proyecto en un ZIP, extráelo
unzip wedding-rsvp.zip
cd wedding-rsvp

# O clona desde Git (si aplica)
git clone <url-del-repositorio>
cd wedding-rsvp
```

## 🔧 Paso 2: Verificar la Estructura del Proyecto

Asegúrate de que tienes esta estructura:

```
wedding-rsvp/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/wedding/rsvp/
│       │       ├── config/
│       │       ├── controller/
│       │       ├── exception/
│       │       ├── mapper/
│       │       ├── model/
│       │       ├── repository/
│       │       ├── service/
│       │       └── WeddingRsvpApplication.java
│       └── resources/
│           └── application.yml
├── pom.xml
└── README.md
```

## 📦 Paso 3: Compilar el Proyecto

Abre una terminal en la carpeta del proyecto y ejecuta:

```bash
# Limpiar y compilar
mvn clean install
```

**Salida esperada:**
```
[INFO] BUILD SUCCESS
[INFO] Total time: 15.234 s
```

**Si hay errores:**
- Verifica que estés en la carpeta correcta (donde está pom.xml)
- Verifica que Java 17 esté instalado: `java -version`
- Verifica que Maven esté instalado: `mvn -version`

## ▶️ Paso 4: Ejecutar la Aplicación

### Opción A: Con Maven (Recomendado para desarrollo)

```bash
mvn spring-boot:run
```

### Opción B: Con Java (JAR compilado)

```bash
# Primero compila el JAR
mvn clean package

# Luego ejecútalo
java -jar target/rsvp-service-1.0.0.jar
```

### Opción C: Desde el IDE

1. Abre el proyecto en IntelliJ IDEA o Eclipse
2. Busca la clase `WeddingRsvpApplication.java`
3. Haz clic derecho → Run 'WeddingRsvpApplication'

**Salida esperada al iniciar:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v3.2.0)

INFO  WeddingRsvpApplication - Starting WeddingRsvpApplication...
INFO  WeddingRsvpApplication - Started WeddingRsvpApplication in 3.456 seconds
```

## 🎯 Paso 5: Verificar que la Aplicación Está Funcionando

### 5.1 Health Check con cURL

```bash
curl http://localhost:8080/api/v1/guests/health
```

**Respuesta esperada:**
```json
{
  "status": "UP",
  "service": "Wedding RSVP Service"
}
```

### 5.2 Health Check con navegador

Abre tu navegador y ve a:
```
http://localhost:8080/api/v1/guests/health
```

### 5.3 Consola H2 (Base de Datos)

Accede a la consola H2 en:
```
http://localhost:8080/api/h2-console
```

**Credenciales:**
- JDBC URL: `jdbc:h2:mem:weddingdb`
- Username: `sa`
- Password: (dejar vacío)

## 🧪 Paso 6: Probar la API con Postman

### 6.1 Configurar Postman

1. Abre Postman
2. Crea una nueva Collection llamada "Wedding RSVP"
3. Crea una variable de entorno:
   - Variable: `base_url`
   - Valor: `http://localhost:8080/api`

### 6.2 Crear un Invitado

**Request:**
```
POST {{base_url}}/v1/guests
Content-Type: application/json
```

**Body (JSON):**
```json
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

**Respuesta esperada: 201 Created**

### 6.3 Obtener Todos los Invitados

**Request:**
```
GET {{base_url}}/v1/guests?page=0&size=10
```

**Respuesta esperada: 200 OK** con lista de invitados

### 6.4 Obtener Estadísticas

**Request:**
```
GET {{base_url}}/v1/guests/stats
```

**Respuesta esperada:**
```json
{
  "totalGuests": 4,
  "attendingGuests": 3,
  "notAttendingGuests": 1,
  "attendanceRate": "75.00%"
}
```

## 📊 Paso 7: Ver los Datos en la Base de Datos

1. Ve a la consola H2: http://localhost:8080/api/h2-console
2. Conéctate con las credenciales mencionadas
3. Ejecuta queries SQL:

```sql
-- Ver todos los invitados
SELECT * FROM GUESTS;

-- Ver todos los adultos
SELECT * FROM ADULTS;

-- Ver invitados que asisten
SELECT * FROM GUESTS WHERE ATTENDING = TRUE;

-- Contar invitados por tipo de menú
SELECT MENU, COUNT(*) FROM ADULTS GROUP BY MENU;
```

## 🔍 Paso 8: Probar Validaciones

### Error: Número de adultos no coincide
```json
POST {{base_url}}/v1/guests
{
  "attending": true,
  "numberOfAdults": 2,
  "numberOfChildren": 0,
  "adults": [
    {
      "fullName": "Solo Uno",
      "menu": "CARNE",
      "adultOrder": 1
    }
  ]
}
```
**Respuesta esperada: 400 Bad Request**

### Error: Email inválido
```json
POST {{base_url}}/v1/guests
{
  "attending": true,
  "numberOfAdults": 1,
  "numberOfChildren": 0,
  "adults": [
    {
      "fullName": "Test User",
      "menu": "CARNE",
      "adultOrder": 1
    }
  ],
  "contactEmail": "email-invalido"
}
```
**Respuesta esperada: 400 Bad Request con error de validación**

## 🛑 Paso 9: Detener la Aplicación

- Si ejecutaste con Maven: Presiona `Ctrl + C` en la terminal
- Si ejecutaste desde el IDE: Haz clic en el botón de Stop

## 📝 Endpoints Disponibles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/v1/guests` | Crear invitado |
| GET | `/api/v1/guests/{id}` | Obtener invitado por ID |
| GET | `/api/v1/guests?page=0&size=10` | Listar todos (paginado) |
| GET | `/api/v1/guests/attending` | Listar los que asisten |
| GET | `/api/v1/guests/not-attending` | Listar los que NO asisten |
| GET | `/api/v1/guests/stats` | Obtener estadísticas |
| GET | `/api/v1/guests/health` | Health check |

## 🚨 Solución de Problemas Comunes

### Problema 1: Puerto 8080 ya en uso
```
Error: Port 8080 is already in use
```
**Solución:** Cambia el puerto en `application.yml`:
```yaml
server:
  port: 8081
```

### Problema 2: Java version incorrecta
```
Error: Invalid target release: 17
```
**Solución:** Verifica tu versión de Java:
```bash
java -version
```
Debe ser 17 o superior.

### Problema 3: Maven no encuentra dependencias
```
Error: Could not resolve dependencies
```
**Solución:**
```bash
mvn clean install -U
```

### Problema 4: La aplicación no inicia
```
Error: Application failed to start
```
**Solución:** Revisa los logs en la consola para ver el error específico.

## 📚 Recursos Adicionales

- Documentación de Spring Boot: https://spring.io/projects/spring-boot
- Documentación de Spring Data JPA: https://spring.io/projects/spring-data-jpa
- Documentación de Bean Validation: https://beanvalidation.org/

## 🎓 Próximos Pasos

1. ✅ Familiarízate con todos los endpoints
2. ✅ Prueba diferentes escenarios de validación
3. ✅ Analiza la estructura del código
4. ✅ Modifica y agrega nuevas funcionalidades
5. ✅ Integra con tu frontend React

## 💡 Tips

- Usa la consola H2 para verificar que los datos se guardan correctamente
- Revisa los logs de la aplicación para entender el flujo
- Los datos se pierden al reiniciar (H2 en memoria)
- Para persistir datos, cambia a MySQL siguiendo el README

## ✅ Checklist de Verificación

- [ ] Java 17 instalado
- [ ] Maven instalado
- [ ] Proyecto compilado sin errores
- [ ] Aplicación iniciada correctamente
- [ ] Health check responde OK
- [ ] Puedo crear un invitado con POST
- [ ] Puedo listar invitados con GET
- [ ] Puedo ver estadísticas
- [ ] Accedo a la consola H2
- [ ] Entiendo la estructura del proyecto

¡Felicidades! 🎉 Ya tienes tu CRUD de invitados funcionando.
