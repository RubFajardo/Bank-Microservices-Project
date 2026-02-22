Banking Microservices Ecosystem

Este proyecto consiste en un sistema de microservicios distribuido diseñado para simular una infraestructura bancaria moderna.

Se centra en:

Escalabilidad

Resiliencia

Comunicación asíncrona basada en eventos

Tecnologías Principales

Java 17 (Microsoft OpenJDK)

Spring Boot 3.4.2 – Framework principal para el desarrollo de microservicios

RabbitMQ – Message Broker para la comunicación asíncrona entre servicios

Docker & Docker Compose – Contenedorización de la infraestructura (RabbitMQ, DBs)

Spring Security & JWT – Autenticación y autorización basada en tokens

Spring Data JPA – Abstracción de persistencia de datos

H2 Database – Base de datos en memoria para desarrollo ágil y tests

Spring Boot Actuator – Monitorización y salud de los servicios en tiempo real

Arquitectura del Sistema

El sistema utiliza una Arquitectura Orientada a Eventos (EDA) para desacoplar los servicios.

Resiliencia y Observabilidad

Dead Letter Queues (DLQ)
Implementación de colas de error para asegurar que ningún mensaje se pierda en caso de fallo técnico.

Retry Strategy
Mecanismo de reintentos automáticos con exponential backoff.

Endpoints de Salud
Uso de Actuator para verificar la conectividad con el Broker y el estado de la memoria.

Cómo Empezar
1. Levantar Infraestructura
docker-compose up -d
2. Lanzar Servicios

Cada servicio puede arrancarse de forma independiente desde el monorepo.

3. Acceso a DB

Cada microservicio tiene su consola H2 independiente:

http://localhost:8080/h2-console
