Banking Microservices Ecosystem

Este proyecto consiste en un sistema de microservicios distribuido diseñado para simular una infraestructura bancaria moderna.

Se centra en:

Escalabilidad

Resiliencia

Comunicación asíncrona basada en eventos

Tecnologías Principales:

Java 17 (Microsoft OpenJDK)

Spring Boot 3.4.2 – Framework principal para el desarrollo de microservicios

🛠️ Desafíos Técnicos y Soluciones

Comunicación entre servicios: Implementé RabbitMQ para resolver el acoplamiento entre microservicios, logrando una comunicación asíncrona basada en eventos. Esto permite que, si el servicio de transacciones está ocupado, el sistema de auditoría o notificaciones pueda procesar la información a su propio ritmo sin bloquear al usuario.

Seguridad Distribuida: Utilicé Spring Security y JWT para resolver el problema de la autenticación en sistemas stateless. Esto permite que cada microservicio valide la identidad del usuario de forma independiente sin necesidad de consultar una sesión centralizada, mejorando la escalabilidad.

Persistencia y Agilidad: Usé H2 Database con Spring Data JPA para resolver la necesidad de un desarrollo rápido y pruebas volátiles. Esto me permite levantar el entorno completo en segundos sin depender de una base de datos externa pesada durante la fase de construcción.

Gestión de Infraestructura: Implementé Docker & Docker Compose para resolver el problema del "en mi máquina funciona". Toda la infraestructura (RabbitMQ, bases de datos, etc.) se despliega de forma idéntica en cualquier entorno con un solo comando.

Tolerancia a Fallos: Configuré Dead Letter Queues (DLQ) y Retry Strategies (Exponential Backoff) para resolver la posible pérdida de datos. Si un mensaje falla (por ejemplo, por un error de red), el sistema lo reintenta de forma inteligente o lo mueve a una cola de inspección, garantizando que ninguna transacción bancaria se pierda.

Monitoreo en Tiempo Real: Usé Spring Boot Actuator para resolver la falta de visibilidad en sistemas distribuidos, permitiendo conocer el estado de salud y consumo de memoria de cada microservicio al instante.

Decidí usar EDA para resolver el problema de la fragilidad de las arquitecturas monolíticas. Al desacoplar los servicios, si el Audit-Service falla, el Auth-Service puede seguir registrando usuarios sin interrupciones.

Cómo Empezar
1. Levantar Infraestructura
docker-compose up -d
2. Lanzar Servicios

Cada servicio puede arrancarse de forma independiente desde el monorepo.

3. Acceso a DB

Cada microservicio tiene su consola H2 independiente:

http://localhost:8080/h2-console
