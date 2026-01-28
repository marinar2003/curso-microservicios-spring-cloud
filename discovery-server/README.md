# Ecosistema de Microservicios con Spring Cloud 

Este repositorio contiene el desarrollo práctico de mi aprendizaje sobre arquitecturas distribuidas. El objetivo es construir un sistema escalable utilizando el ecosistema de Spring.

## Tecnologías utilizadas
* **Java 21**
* **Spring Boot 3.x**
* **Spring Cloud Netflix Eureka** (Service Discovery)
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

## 🚀 Cómo ejecutarlo
1. Clonar el repositorio.
2. Abrir el proyecto `discovery-server` en un IDE (IntelliJ).
3. Ejecutar la clase `DiscoveryServerApplication`.
4. Acceder a `http://localhost:8761` para ver el dashboard.