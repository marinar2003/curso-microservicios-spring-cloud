# Ecosistema de Microservicios con Spring Cloud

Este repositorio contiene una arquitectura distribuida completa, diseñada para ser escalable, resiliente y monitoreable. El sistema simula un flujo de e-commerce donde la gestión de pedidos e inventario están desacoplados y supervisados en tiempo real.



## Tecnologías y Stack
* **Java 21** & **Spring Boot 3.4.2**
* **Spring Cloud Netflix Eureka** (Service Discovery)
* **Spring Cloud Config** (Configuración centralizada con Git)
* **Spring Cloud Gateway** (Punto de entrada único y ruteo dinámico)
* **Spring Cloud OpenFeign** (Comunicación declarativa entre servicios)
* **Resilience4j** (Implementación de Circuit Breaker y Resiliencia)
* **Micrometer + Zipkin** (Trazabilidad distribuida y Observabilidad)
* **Maven** (Gestión de dependencias)

---

## Estructura del Ecosistema

### 1. Infraestructura Core
* **Discovery Server (Eureka):** El directorio central donde todos los servicios se registran para permitir la comunicación mediante nombres lógicos en lugar de IPs fijas. (Puerto `8761`).
* **Config Server:** El cerebro de la configuración. Centraliza los archivos `.properties` en un repositorio Git, permitiendo gestionar cambios sin reconstruir los microservicios. (Puerto `8888`).
* **API Gateway:** El único punto de entrada para los clientes. Se encarga del ruteo inteligente hacia los microservicios internos. (Puerto `9095`).

### 2. Servicios de Negocio
* **Inventory Service:** Gestiona la existencia de productos. Está diseñado para escalar horizontalmente (varias instancias) para soportar alta carga.
* **Order Service:** Orquesta la creación de pedidos. Utiliza **Feign Client** para consultar al Inventario y toma decisiones basadas en la disponibilidad.

---

## Observabilidad y Resiliencia

### Trazabilidad Distribuida (Zipkin)
Hemos implementado **Micrometer Tracing** para dar visibilidad al viaje de cada petición.
* Cada solicitud genera un `Trace ID` único que persiste a través de todos los servicios.
* **Zipkin** recolecta estos datos, permitiendo visualizar latencias y detectar exactamente en qué punto de la cadena ocurrió un error.



### Tolerancia a Fallos y Balanceo
* **Circuit Breaker:** Gracias a Resilience4j, si el Inventory Service falla, el Order Service no se bloquea; activa un método de "fallback" para mantener la experiencia del usuario.
* **Load Balancer:** Implementado con Round Robin. Si levantas múltiples instancias del inventario, el sistema reparte las peticiones equitativamente entre ellas automáticamente.

---

## Cómo ejecutar el ecosistema

Para garantizar la integridad del flujo, inicia los servicios en este orden:

1.  **Discovery Server**: Esperar a que el dashboard en `http://localhost:8761` esté activo.
2.  **Config Server**: Asegurarse de que el repositorio de configuración sea accesible.
3.  **Inventory Service**: Puedes iniciar una instancia en el puerto `8081` y otra en el `9090` (usando `-Dserver.port=9090`).
4.  **Order Service**: Se registrará en Eureka y buscará la configuración en el Config Server.
5.  **API Gateway**: El último en iniciar para comenzar a recibir peticiones.

---

### Prueba de Integración End-to-End

Ya no es necesario llamar a los servicios individuales. Todas las peticiones deben pasar por el Gateway:

* **URL:** `GET http://localhost:9095/api/orders/create?code=samsung`
* **Flujo Interno:** `Postman` ➔ `API Gateway` ➔ `Order Service` ➔ `Inventory Service` (vía Feign).

---

### Notas de Implementación
* Se utiliza **Spring Boot