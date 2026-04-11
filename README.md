# boat-tours-system

## Problem Description

Nowadays, many platforms allow people to connect and offer services or experiences; however, most of these systems are built as monolithic applications or have tightly coupled components. This makes them difficult to maintain, scale, and evolve over time.

This project aims to address this issue by designing a simplified system where local community members can publish boat tours and users can explore available options. The solution focuses on maintaining a clear separation of responsibilities through independent microservices, allowing each part of the system to evolve without affecting others.

---

## Introduction

This project consists of the development of a distributed system based on a **microservices architecture**, using **Micronaut** for the backend and **React** for the frontend.

The system is organized as a **monorepo**, containing multiple independent microservices, each responsible for a specific domain and equipped with its own database, business logic, and internal layers. Communication between services and the frontend is handled through REST APIs.

Core functionalities include the creation and retrieval of boat tours, enabling guides to publish tour information and users to browse available options.

The project applies key software design principles such as **low coupling, high cohesion, and service autonomy**, ensuring scalability and maintainability.

Additionally, all code is generated using AI-assisted development tools, with prompts and decisions documented as part of the implementation process.

#AI used: ChatGPT with deep thinking
##Prompts Used

###Prompt 1:
Rol: full stack developer Instrucciones: Desarrollar un programa con estas caracteristicas Arquitectura: Micronaut + microservices + REST Monorepo con múltiples microservicios independientes Cada microservicio con sus propias capas: business logic, repositories, model Separación estricta sin compartir código entre microservicios Base de datos pequeña por servicio (puede ser H2 o SQLite) Operación de escritura: POST /tours en el microservicio de tours para crear un tour con nombre, ubicación y precio Operación de lectura: GET /tours para listar tours disponibles Capacidad de desplegar y ejecutar cada microservicio por separado Frontend: React + Tailwind consumiendo los microservicios Scripts individuales por microservicio para dev y prod Todo ejecutándose localmente con configuración mínima Contexto: Microservicios con Micronaut para tours en lanchas de personas locales de la comunidad sin permisos de turismo. Construyan un sistema distribuido donde guías locales publiquen tours en lanchas y usuarios puedan consultarlos, implementando microservicios pequeños y bien aislados dentro de un monorepo. Output: Programa dividido en 2 carpetas principales frontend y backend, y explicación de lo realizado


###Prompt 2: Configura el application.yaml de tour y guide para que se conecten a los puertos 8081 y 8082 respectivamente
