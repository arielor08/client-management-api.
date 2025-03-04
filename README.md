# **Client Management API**

📅 **Última actualización**: 04 de marzo de 2025

👨‍💻 **Desarrollador**: Edwin Ariel Ortega Moreta

##  Introducción

Esta API RESTful proporciona funcionalidades para gestionar clientes. Se ha desarrollado utilizando **Java 21**, **Quarkus**, **Hibernate (JPA)** y **PostgreSQL** como base de datos. Además, la API sigue buenas prácticas como validaciones con **Jakarta Validation**, manejo de errores y pruebas unitarias con **JUnit y RestAssured**.

---

##  Tecnologías Utilizadas

- **Java 21**
- **Quarkus**
- **Hibernate / JPA**
- **PostgreSQL**
- **RestAssured (para pruebas)**
- **MicroProfile OpenAPI (para documentación)**
- **Mockito (para pruebas unitarias)**

---

## Diagrama de Arquitectura

https://www.mermaidchart.com/raw/e931fc50-f982-4f69-b3e7-675a61dd4946?theme=light&version=v0.1&format=svg

## Configuración

### **1️ Configuración de la Base de Datos**

La API está configurada para conectarse a una base de datos PostgreSQL llamada **customer-db**. Para usar variables de entorno en lugar de valores fijos, configura lo siguiente:

📄 **`application.properties`**

```

%dev.quarkus.datasource.jdbc.url=jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:customer-db}
%dev.quarkus.datasource.username=${DB_USER:postgres}
%dev.quarkus.datasource.password=${DB_PASSWORD:password}
quarkus.hibernate-orm.database.generation=update

```

---

## Endpoints de la API

 **Base URL**: `http://localhost:8080/clients`

### **2 Obtener Todos los Clientes**

**Método**: `GET`

 **Endpoint**: `/clients`

 **Descripción**: Devuelve una lista con todos los clientes registrados.

 **Ejemplo de Respuesta (200 OK)**:

```json
[
    {
        "identification": "123456789101",
        "firstName": "Ariel",
        "lastName": "Ortega",
        "email": "ariel@example.com",
        "phone": "1234567890",
        "country": "DO",
        "address": "123 Main St"
    }
]

```

---

### 3 Obtener Cliente por Identificación

 **Método**: `GET`

 **Endpoint**: `/clients/{identification}`

 **Descripción**: Obtiene un cliente según su identificación única.

 **Ejemplo de Respuesta (200 OK)**:

```json
{
    "identification": "123456789101",
    "firstName": "Ariel",
    "lastName": "Ortega",
    "email": "ariel@example.com",
    "phone": "1234567890",
    "country": "DO",
    "address": "123 Main St"
}

```

 **Respuesta en caso de error (404 NOT FOUND)**:

```json
{
    "error": "Client not found"
}

```

---

###  4 Obtener Clientes por País

 **Método**: `GET`

 **Endpoint**: `/clients/country/{country}`

 **Descripción**: Devuelve una lista de clientes registrados en un país específico.

 **Ejemplo de Respuesta (200 OK)**:

```json
[
    {
        "identification": "123456789101",
        "firstName": "Ariel",
        "lastName": "Ortiz",
        "email": "ariel@example.com",
        "phone": "1234567890",
        "country": "DO",
        "address": "123 Main St"
    }
]

```

 **Respuesta en caso de error (404 NOT FOUND)**:

```json
{
    "error": "No clients found for this country"
}

```

---

### 5 Crear un Nuevo Cliente

 **Método**: `POST`

 **Endpoint**: `/clients`

 **Descripción**: Registra un nuevo cliente en la base de datos.

 **Ejemplo de Petición**:

```json
{
    "identification": "123456789101",
    "firstName": "Ariel",
    "lastName": "Ortiz",
    "email": "ariel@example.com",
    "phone": "1234567890",
    "country": "DO",
    "address": "123 Main St"
}

```

 **Ejemplo de Respuesta (201 CREATED)**:

```json
{
    "message": "Client created successfully",
    "identification": "123456789101"
}
```

 **Errores Posibles (400 BAD REQUEST)**:

```json
{
    "error": "Email is required"
}
```

---

### 6 Actualizar Cliente

 **Método**: `PUT`

 **Endpoint**: `/clients/{identification}`

 **Descripción**: Actualiza los datos de un cliente. Solo se pueden modificar **email, teléfono y país**.

 **Ejemplo de Petición**:

```json
{
    "email": "newemail@example.com",
    "phone": "9876543210",
    "country": "DO"
}

```

 **Ejemplo de Respuesta (200 OK)**:

```json
{
    "message": "Client updated successfully",
    "identification": "123456789101"
}

```

 **Errores Posibles (404 NOT FOUND)**:

```json
{
    "error": "Client not found"
}

```

---

### 7 Eliminar Cliente 

 **Método**: `DELETE`

 **Endpoint**: `/clients/{identification}`

 **Descripción**: Elimina un cliente según su identificación.

 **Ejemplo de Respuesta (200 OK)**:

```json
{
    "message": "Client deleted successfully"
}

```

 **Errores Posibles (404 NOT FOUND)**:

```json
{
    "error": "Client not found"
}

```

---

## ** Pruebas Unitarias**

Para ejecutar las pruebas unitarias en Quarkus, usa el siguiente comando:

```bash
bash
CopyEdit
mvn test

```

Las pruebas cubren:

- Creación de clientes.
- Obtención de clientes por identificación y país.
- Actualización y eliminación de clientes.

Ejemplo de una prueba en **JUnit con RestAssured**:

```java
@Test
public void testCreateClient() {
    String clientJson = "{ \"identification\": \"40212022541\", \"firstName\": \"Ariel\", \"lastName\": \"Ortiz\", \"email\": \"ariel@example.com\", \"phone\": \"1234567890\", \"country\": \"DO\", \"address\": \"123 Main St\" }";

    given()
        .body(clientJson)
        .contentType(ContentType.JSON)
        .when()
        .post("/clients")
        .then()
        .statusCode(201)
        .body("identification", equalTo("123456789101"));
}

```

---

## Conclusión

Bajo todo mi esfuerzo y limitado conocimiento hice posible este desarrollo, gracias por la oportunidad y el tiempo para intentar hacerme parte del Equipo.

Saludos Cordiales