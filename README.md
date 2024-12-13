# Proyecto Base Implementando Clean Architecture

## Antes de Iniciar

Empezaremos por explicar los diferentes componentes del proyectos y partiremos de los componentes externos, continuando con los componentes core de negocio (dominio) y por último el inicio y configuración de la aplicación.

Lee el artículo [Clean Architecture — Aislando los detalles](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a)

# Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## Domain

Es el módulo más interno de la arquitectura, pertenece a la capa del dominio y encapsula la lógica y reglas del negocio mediante modelos y entidades del dominio.

## Usecases

Este módulo gradle perteneciente a la capa del dominio, implementa los casos de uso del sistema, define lógica de aplicación y reacciona a las invocaciones desde el módulo de entry points, orquestando los flujos hacia el módulo de entities.

## Infrastructure

### Helpers

En el apartado de helpers tendremos utilidades generales para los Driven Adapters y Entry Points.

Estas utilidades no están arraigadas a objetos concretos, se realiza el uso de generics para modelar comportamientos
genéricos de los diferentes objetos de persistencia que puedan existir, este tipo de implementaciones se realizan
basadas en el patrón de diseño [Unit of Work y Repository](https://medium.com/@krzychukosobudzki/repository-design-pattern-bc490b256006)

Estas clases no puede existir solas y debe heredarse su compartimiento en los **Driven Adapters**

### Driven Adapters

Los driven adapter representan implementaciones externas a nuestro sistema, como lo son conexiones a servicios rest,
soap, bases de datos, lectura de archivos planos, y en concreto cualquier origen y fuente de datos con la que debamos
interactuar.

### Entry Points

Los entry points representan los puntos de entrada de la aplicación o el inicio de los flujos de negocio.

## Application

Este módulo es el más externo de la arquitectura, es el encargado de ensamblar los distintos módulos, resolver las dependencias y crear los beans de los casos de use (UseCases) de forma automática, inyectando en éstos instancias concretas de las dependencias declaradas. Además inicia la aplicación (es el único módulo del proyecto donde encontraremos la función “public static void main(String[] args)”.

**Los beans de los casos de uso se disponibilizan automaticamente gracias a un '@ComponentScan' ubicado en esta capa.**


# **Worker Java Reactivo para Procesamiento de Pedidos**

Este proyecto implementa un Worker reactivo desarrollado con Spring Boot para procesar pedidos provenientes de Kafka, enriquecerlos con información de productos y clientes obtenida desde APIs externas, y almacenarlos en MongoDB.

## **Descripción**

Este Worker reactivo procesa pedidos en un entorno de comercio electrónico. Los pedidos son consumidos desde un tópico Kafka, complementados con información adicional de productos y clientes, y almacenados de forma asíncrona en MongoDB.

### **Flujo General**
1.	Consume mensajes del tópico orders en Kafka.
2.	Extrae información básica de los pedidos.
3.	Consulta dos APIs externas:
   - API Go: Para obtener detalles de los productos.
   - API GraphQL (Nest): Para obtener datos del cliente.
4. Combina la información de los productos y del cliente con el pedido original.
5. Almacena el pedido enriquecido en MongoDB.

### **Requisitos**

#### **Tecnologías**
* Java 21+
* Spring Boot 3.3.x
* Kafka 3.x
* MongoDB
* Docker (Opcional): Para ejecutar servicios como Kafka y MongoDB localmente.

## **Instalación**

1. Clonar el Repositorio
```bash
git clone https://github.com/diegogfhjk/worker-java-reactivo.git
cd worker-reactivo-pedidos
```

## **Configuraciones Requeridas**

El archivo de configuración (application.yml) contiene los parámetros necesarios para ejecutar el proyecto. A continuación, se explican las configuraciones clave y cómo adaptarlas según tu entorno:

### **Configuraciones del Servidor**
```yml
server:
  port: 8082
spring:
  application:
    name: "WokerReactivo"
```
* server.port: Puerto en el que la aplicación estará escuchando. Por defecto: 8082.

## **Base de Datos (MongoDB)**
```yml
spring:
  data:
    mongodb:
      uri: "mongodb://localhost:27017/orders"
```
* spring.data.mongodb.uri: URI de conexión a MongoDB. 
  * Cambiar si estás usando un servicio de MongoDB en la nube (como Atlas) o en otro host/puerto.

## **API REST para Productos (Go)**
```yml
adapter:
  restconsumer:
    url: "http://localhost:8080"
```
* adapter.restconsumer.url: URL base de la API REST que proporciona los detalles de los productos.
  * Cambiar si la API Go está en un host/puerto diferente o expuesta en un entorno de producción.

## **API GraphQL para Clientes (Nest)**
```yml
adapter:
  grapql:
    url: "http://localhost:3000/graphql"
```
* adapter.grapql.url: URL base de la API GraphQL que proporciona información sobre los clientes.
  * Cambiar si la API está en otro host/puerto o es accesible en una URL diferente.

## **Kafka**
```yml
adapter:
  kafka:
    url: "localhost:9092"
```
* adapter.kafka.url: Dirección del servidor Kafka. Por defecto: localhost:9092.
  * Cambiar si Kafka está en otro host o puerto, o si estás utilizando un clúster Kafka en la nube.

## **Resilience4j (Circuit Breakers)**
```yml
resilience4j:
  circuitbreaker:
    instances:
      testGet:
        registerHealthIndicator: true
        failureRateThreshold: 50
        slowCallRateThreshold: 50
        slowCallDurationThreshold: "2s"
        permittedNumberOfCallsInHalfOpenState: 3
        slidingWindowSize: 10
        minimumNumberOfCalls: 10
        waitDurationInOpenState: "10s"
```
* testGet y testPost: Configuración de Circuit Breakers para las llamadas a las APIs externas.
  * failureRateThreshold: Porcentaje máximo de fallos permitido antes de abrir el circuito.
  * slowCallRateThreshold: Porcentaje máximo de llamadas lentas permitido.
  * slowCallDurationThreshold: Duración que define una llamada lenta (2 segundos por defecto).
  * waitDurationInOpenState: Tiempo que el circuito permanecerá abierto antes de intentar reabrirlo.

Para incluir una consideración importante en un README, utiliza una sección destacada o un bloque de advertencia que resalte claramente la información crítica. Aquí tienes un ejemplo de cómo hacerlo:

## ⚠️ Consideraciones Importantes
### 1.	Servicios Necesarios:<br>
Antes de ejecutar este proyecto, asegúrate de que los siguientes servicios estén en funcionamiento:
* Kafka: Debe estar configurado y corriendo en el host y puerto especificados en application.yml.
* MongoDB: Asegúrate de que la base de datos esté disponible y accesible.


## **Ejecución**
1. Compila el proyecto:
```bash
bash ./gradlew clean build
```

2. Ejecuta la aplicación:
```bash
bash ./gradlew bootRun
```