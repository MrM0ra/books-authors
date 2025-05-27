# BooksAuthors API REST

API REST para administrar libros y autores usando un backend PL/SQL y Spring Boot.

---
## 🚀 Cómo ejecutar el proyecto

Sigue estos pasos para levantar el entorno completo (backend + base de datos Oracle) usando Docker y Docker Compose:

### ✅ Requisitos previos

- Docker instalado
- Docker Compose instalado
- Java 17+
- Maven instalado

### 🛠️ Paso a paso
### Clona el repositorio:

```bash
git clone https://github.com/tu-usuario/tu-repo.git
cd tu-repo
```

### Construye la imagen del backend:

```bash
mvn clean package -DskipTests
```

### Levanta los servicios con Docker Compose:

```bash
docker-compose up --build
```

Esto levantará:

### - Una base de datos Oracle usando gvenzl/oracle-free
### - El backend Java (Spring Boot), una vez la base de datos esté saludable

Verifica que todo esté corriendo:

Backend: http://localhost:8080

Oracle DB: puerto 1521 (no expone UI, pero puedes conectarte vía SQL Developer o similar)

🧪 Endpoints disponibles

## Endpoints disponibles

### Authors

| Método | Endpoint            | Descripción                     |
|--------|---------------------|--------------------------------|
| GET    | `/api/authors`      | Obtener todos los autores       |
| GET    | `/api/authors/{id}` | Obtener autor por ID            |
| POST   | `/api/authors`      | Crear nuevo autor               |
| PUT    | `/api/authors/{id}` | Actualizar autor existente     |
| DELETE | `/api/authors/{id}` | Eliminar autor por ID           |

---

### Books

| Método | Endpoint                    | Descripción                      |
|--------|-----------------------------|---------------------------------|
| GET    | `/api/books`                | Obtener todos los libros          |
| GET    | `/api/books/{id}`           | Obtener libro por ID              |
| POST   | `/api/books`                | Crear nuevo libro                 |
| PUT    | `/api/books/{id}`           | Actualizar libro existente        |
| DELETE | `/api/books/{id}`           | Eliminar libro por ID             |
| GET    | `/api/books/author/{authorId}` | Obtener libros por autor         |

---

## Ejemplos de uso con `curl`

### Autores

**Listar todos los autores**

```bash
curl -X GET http://localhost:8080/api/authors
```

**Obtener un autor por ID**

```bash
curl -X GET http://localhost:8080/api/authors/1
```

**Crear un autor**

```bash
curl -X POST http://localhost:8080/api/authors \
  -H "Content-Type: application/json" \
  -d '{"id":1,"name":"Gabriel García Márquez"}'
```

**Actualizar un autor**

```bash
curl -X PUT http://localhost:8080/api/authors/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Gabo García Márquez"}'
```

**Eliminar un autor**

```bash
curl -X DELETE http://localhost:8080/api/authors/1
```

### Libros

**Listar todos los libros**

```bash
curl -X GET http://localhost:8080/api/books
```

**Obtener libro por ID**

```bash
curl -X GET http://localhost:8080/api/books/1
```

**Crear un libro**

```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"id":1,"title":"Cien años de soledad","authorId":1}'
```

**Actualizar un libro**

```bash
curl -X PUT http://localhost:8080/api/books/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Cien años de soledad - Edición revisada","authorId":1}'
```

**Eliminar un libro**

```bash
curl -X DELETE http://localhost:8080/api/books/1
```

**Obtener todos los libros de un autor**

```bash
curl -X GET http://localhost:8080/api/books/author/1
```


## Ejecución de la aplicación

### 1. Asegúrate de tener Oracle Database corriendo y accesible, con las tablas y procedimientos creados.

### 2. Configura las variables de conexión en application.properties o en variables de entorno.

### 3. Ejecuta la aplicación:

```bash
mvn spring-boot:run
```

La API estará disponible en: http://localhost:8080
