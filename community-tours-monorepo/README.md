# Community Boat Tours Monorepo

Monorepo con dos carpetas principales:

- `backend`: microservicios independientes con Micronaut + REST + H2
- `frontend`: aplicación React + Tailwind para consumir los microservicios

## Arquitectura

### Backend

Hay **dos microservicios totalmente separados**:

1. `tours-service`
   - `POST /tours`
   - `GET /tours`
   - Guarda tours locales en lancha con nombre, ubicación y precio

2. `guides-service`
   - `POST /guides`
   - `GET /guides`
   - Guarda guías locales con nombre, comunidad y nombre de la lancha

Cada microservicio tiene su propia:

- lógica de negocio (`service`)
- capa de acceso a datos (`repository`)
- modelo (`model`)
- controlador REST (`controller`)
- base de datos H2 en archivo local
- `pom.xml`
- scripts `dev` y `prod`
- imagen Docker propia

> No se comparte código entre microservicios.

### Frontend

Aplicación React + Vite + Tailwind que:

- consulta el listado de tours
- crea tours nuevos
- consulta el listado de guías locales
- registra guías nuevas
- consume ambos microservicios por separado

## Estructura

```text
community-tours-monorepo/
├── backend/
│   ├── tours-service/
│   └── guides-service/
├── frontend/
└── docker-compose.yml
```

## Requisitos mínimos

La forma más simple de ejecutarlo es con:

- Docker Desktop

Opcionalmente también puedes usar:

- Java 21+
- Maven
- Node 20+

## Ejecutar todo con Docker Compose

Desde la raíz del proyecto:

```bash
docker compose up --build
```

Servicios:

- Frontend: `http://localhost:5173`
- Tours service: `http://localhost:8081/tours`
- Guides service: `http://localhost:8082/guides`

## Ejecutar por separado

### Tours service

Linux/macOS:

```bash
cd backend/tours-service
./scripts/dev.sh
```

Windows:

```bat
cd backend\tours-service
scripts\dev.bat
```

### Guides service

Linux/macOS:

```bash
cd backend/guides-service
./scripts/dev.sh
```

Windows:

```bat
cd backend\guides-service
scripts\dev.bat
```

### Frontend

Linux/macOS:

```bash
cd frontend
./scripts/dev.sh
```

Windows:

```bat
cd frontend
scripts\dev.bat
```

## Endpoints principales

### Tours service

#### Crear tour

```http
POST /tours
Content-Type: application/json
```

Body:

```json
{
  "name": "Tour al manglar",
  "location": "Isla Chira",
  "price": 35.50
}
```

#### Listar tours

```http
GET /tours
```

### Guides service

#### Crear guía

```http
POST /guides
Content-Type: application/json
```

Body:

```json
{
  "name": "Marvin Solís",
  "community": "Paquera",
  "boatName": "La Sirenita"
}
```

#### Listar guías

```http
GET /guides
```

## Decisiones de diseño

- Se usó **Micronaut** para mantener microservicios ligeros y rápidos.
- Se usó **H2 por archivo** para que cada servicio tenga su propia base pequeña y simple.
- El frontend hace la composición de la información; los microservicios no dependen entre sí.
- Cada servicio puede correr solo, compilarse solo y desplegarse solo.
- La configuración es mínima y se apoya en Docker para evitar instalar herramientas extra en la máquina.


## Updated build fix
This package includes the runtime dependencies required for Micronaut 4 YAML configuration and logging (`org.yaml:snakeyaml` and `ch.qos.logback:logback-classic`) so the backend microservices can start correctly in Docker.
