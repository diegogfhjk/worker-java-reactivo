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
•	Java 21+
•	Spring Boot 3.3.x
•	Kafka 3.x
•	MongoDB
•	Docker (Opcional): Para ejecutar servicios como Kafka y MongoDB localmente.

## **Instalación**

1. Clonar el Repositorio
```bash
git clone https://github.com/tu-usuario/worker-reactivo-pedidos.git
cd worker-reactivo-pedidos
```