# Ecosistema de Microservicios con Spring Cloud 

Este repositorio contiene el desarrollo práctico de mi aprendizaje sobre arquitecturas distribuidas. El objetivo es construir un sistema escalable utilizando el ecosistema de Spring.

## Tecnologías utilizadas
* **Java 21**
* **Spring Boot 3.x**
* **Spring Cloud Netflix Eureka** (Service Discovery)
* **Spring Cloud Config** (Configuración centralizada)
* **Spring Boot Actuator** (Monitoreo y Health Checks)
* **Spring Cloud OpenFeign** (Comunicación declarativa entre servicios)
* **Spring Cloud LoadBalancer** (Balanceo de carga del lado del cliente)
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
* **Estado:** Completado ✅

### 3. Inventory Service
Es el primer microservicio de negocio (Sistemas Distribuidos).
* **Función:** Gestión de inventario. Obtiene su configuración dinámicamente del Config Server y se registra automáticamente en Eureka.
* **Monitoreo:** Incluye **Spring Boot Actuator** para auditoría y chequeo de salud (Health Check).
* **Puerto:** `8081` (asignado vía Config Server).
* **Estado:** Completado ✅

### 4. Order Service
* **Función:** Gestión de pedidos. Se comunica con Inventory Service para verificar stock antes de procesar una orden.
* **Monitoreo:** Utiliza Feign Client para llamadas HTTP simplificadas y Load Balancer para distribuir carga.
* **Puerto:** `8082` 
* **Estado:** Completado ✅

### 🚀 Avances Recientes: Escalabilidad y Comunicación

#### 🔄 Comunicación Dinámica con Feign
Hemos implementado una interfaz declarativa utilizando **Spring Cloud OpenFeign**. Esto permite que el `Order Service` se comunique con el `Inventory Service` de forma elegante, abstrayendo la lógica de las peticiones HTTP.

Ahora el sistema procesa parámetros dinámicos, permitiendo consultar la disponibilidad de cualquier producto mediante una petición simple:

* **Endpoint:** `GET http://localhost:8082/api/orders/create?code=samsung`
* **Flujo:** Postman → Order Service → Feign Client → Inventory Service → Respuesta Combinada.


#### ⚖️ Balanceo de Carga (Round Robin)
Para garantizar la alta disponibilidad y el aprovechamiento de recursos, configuramos **Spring Cloud LoadBalancer**. El sistema es capaz de manejar múltiples instancias del microservicio de inventario simultáneamente.

* **Algoritmo:** Round Robin (reparto equitativo).
* **Funcionamiento:** El LoadBalancer consulta a **Eureka** las instancias activas y alterna las peticiones entre ellas (ej. una petición al puerto `8081` y la siguiente al puerto `9090`), evitando la saturación de un único nodo.

## 🏁 Cómo ejecutar el ecosistema

Siga este orden estrictamente para asegurar que el descubrimiento de servicios y la configuración centralizada funcionen correctamente:

1.  **Discovery Server (Eureka):** * Ejecutar `DiscoveryServerApplication`.
    * **Puerto:** `8761`
    * *Nota:* Esperar a que el dashboard esté disponible en `http://localhost:8761`.

2.  **Config Server:** * Ejecutar `ConfigServerApplication`.
    * **Puerto:** `8888`
    * *Nota:* Asegurarse de que el repositorio de configuración sea accesible.

3.  **Inventory Service:** * Ejecutar `InventoryServiceApplication`.
    * **Puerto:** `8081` (Instancia principal).
    * **Segunda instancia (Opcional):** Para probar el balanceo de carga, configurar `VM Options` con `-Dserver.port=9090` y ejecutar una segunda instancia.

4.  **Order Service:** * Ejecutar `OrderServiceApplication`.
    * **Puerto:** `8082`

    
### 🧪 Prueba de Integración
Una vez que todos los servicios estén en verde en el dashboard de Eureka, podés realizar una prueba de extremo a extremo:

* **URL de prueba:** `GET http://localhost:8082/api/orders/create?code=samsung`
* **Resultado esperado:** Una respuesta que confirme la creación del pedido y muestre qué instancia del inventario (8081 o 9090) respondió a la solicitud.