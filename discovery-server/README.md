# Ecosistema de Microservicios con Spring Cloud 

Este repositorio contiene el desarrollo práctico de mi aprendizaje sobre arquitecturas distribuidas. El objetivo es construir un sistema escalable utilizando el ecosistema de Spring.

## Tecnologías utilizadas
* **Java 21**
* **Spring Boot 3.x**
* **Spring Cloud Netflix Eureka** (Service Discovery)
* **Spring Cloud Config**
* **Spring Boot Actuator**
* **Maven**

## Estructura del Proyecto

### 1. Discovery Server (Eureka)
Es el "corazón" de la arquitectura.
* **Función:** Registro y descubrimiento de servicios.
* **Puerto:** `8761`
* **Estado:** Completado ✅

### 2. Config Server
Es el "cerebro" que centraliza la configuración de todo el ecosistema.
* **Función:** Proveer propiedades externas a los microservicios mediante un repositorio Git local.
* **Puerto:** `8888`
* **Prueba de funcionamiento:** `http://localhost:8888/inventory-service/default`
* **Estado:** Completado ✅

### 3. Inventory Service
Es el primer microservicio de negocio (Sistemas Distribuidos).
* **Función:** Gestión de inventario. Obtiene su configuración dinámicamente del Config Server y se registra automáticamente en Eureka.
* **Monitoreo:** Incluye **Spring Boot Actuator** para auditoría y chequeo de salud (Health Check).
* **Puerto:** `8081` (asignado vía Config Server).
* **Estado:** Completado ✅

## 🚀 Cómo ejecutarlo
1. Clonar el repositorio.
2. Abrir el proyecto `discovery-server` en un IDE (IntelliJ).
3. Ejecutar la clase `DiscoveryServerApplication`.
4. Acceder a `http://localhost:8761` para ver el dashboard.