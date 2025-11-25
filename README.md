# Documentación de la API REST - Productos API

Esta documentación describe los endpoints disponibles en la API de gestión de productos y usuarios.

## Información General

- **Base URL**: `/api`
- **Formato de respuesta**: JSON
- **Autenticación**: Token Bearer (para endpoints protegidos)

---

## 1. Autenticación

Endpoints relacionados con la autenticación y obtención de tokens.

### Iniciar Sesión

Autentica a un usuario y retorna un token de acceso.

- **URL**: `/auth/login`
- **Método**: `POST`
- **Cuerpo de la Petición (JSON)**:

```json
{
  "username": "usuario_ejemplo",
  "password": "password123"
}
```

- **Respuesta Exitosa (200 OK)**:

```json
{
  "accessToken": "usuario_ejemplo:1732570000000",
  "tokenType": "Simple",
  "user": {
    "id": "user_uuid",
    "username": "usuario_ejemplo",
    "email": "usuario@example.com",
    "role": "USER"
  }
}
```

- **Respuesta de Error (401 Unauthorized)**:

```json
{
  "error": "Credenciales inválidas"
}
```

---

## 2. Productos

Gestión del catálogo de productos del restaurante.

### Obtener Todos los Productos

Retorna la lista completa de productos.

- **URL**: `/products`
- **Método**: `GET`
- **Respuesta Exitosa (200 OK)**:

```json
[
  {
    "id": "prod_uuid_1",
    "name": "Hamburguesa Clásica",
    "price": 12.99,
    "category": "Hamburguesas",
    "available": 1
  }
]
```

### Obtener Producto por ID

Busca un producto específico.

- **URL**: `/products/{id}`
- **Método**: `GET`
- **Parámetros de Ruta**:
  - `id`: Identificador único del producto.
- **Respuesta Exitosa (200 OK)**: Objeto `Product`.
- **Respuesta de Error (404 Not Found)**: Si el producto no existe.

### Crear Producto

Agrega un nuevo producto al catálogo.

- **URL**: `/products`
- **Método**: `POST`
- **Cuerpo de la Petición (JSON)**:

```json
{
  "name": "Nueva Hamburguesa",
  "description": "Descripción del producto",
  "price": 15.5,
  "category": "Especialidades",
  "imageUrl": "https://example.com/img.jpg",
  "available": 1,
  "isSpecialty": 0
}
```

- **Respuesta Exitosa (201 Created)**: Objeto `Product` creado.

### Actualizar Producto

Modifica un producto existente.

- **URL**: `/products/{id}`
- **Método**: `PUT`
- **Parámetros de Ruta**:
  - `id`: Identificador del producto a actualizar.
- **Cuerpo de la Petición (JSON)**: Campos a actualizar del objeto `Product`.
- **Respuesta Exitosa (200 OK)**: Objeto `Product` actualizado.
- **Respuesta de Error (404 Not Found)**: Si el producto no existe.

### Eliminar Producto

Elimina un producto del sistema.

- **URL**: `/products/{id}`
- **Método**: `DELETE`
- **Parámetros de Ruta**:
  - `id`: Identificador del producto a eliminar.
- **Respuesta Exitosa (204 No Content)**: Eliminación correcta.
- **Respuesta de Error (404 Not Found)**: Si el producto no existe.

---

## 3. Usuarios

Gestión de usuarios del sistema.

### Obtener Todos los Usuarios

- **URL**: `/users`
- **Método**: `GET`
- **Respuesta Exitosa (200 OK)**: Lista de objetos `User`.

### Obtener Usuario por ID

- **URL**: `/users/{id}`
- **Método**: `GET`
- **Parámetros de Ruta**:
  - `id`: Identificador del usuario.
- **Respuesta Exitosa (200 OK)**: Objeto `User`.
- **Respuesta de Error (404 Not Found)**: Si el usuario no existe.

### Crear o Actualizar Usuario

- **URL**: `/users`
- **Método**: `POST`
- **Cuerpo de la Petición (JSON)**:

```json
{
  "username": "nuevo_usuario",
  "password": "passwordSeguro",
  "email": "nuevo@email.com",
  "name": "Nombre Completo",
  "role": "USER",
  "phone": "555-1234",
  "address": "Calle Falsa 123"
}
```

- **Respuesta Exitosa (201 Created)**: Objeto `User` guardado.

### Eliminar Usuario

- **URL**: `/users/{id}`
- **Método**: `DELETE`
- **Parámetros de Ruta**:
  - `id`: Identificador del usuario.
- **Respuesta Exitosa (204 No Content)**: Eliminación correcta.
- **Respuesta de Error (404 Not Found)**: Si el usuario no existe.

---

## Modelos de Datos

### Product (Producto)

| Campo         | Tipo     | Descripción                            |
| :------------ | :------- | :------------------------------------- |
| `id`          | String   | Identificador único (UUID).            |
| `name`        | String   | Nombre del producto.                   |
| `description` | String   | Descripción detallada.                 |
| `price`       | Double   | Precio unitario.                       |
| `category`    | String   | Categoría (ej. Hamburguesas, Bebidas). |
| `imageUrl`    | String   | URL de la imagen.                      |
| `available`   | Integer  | 1 = Disponible, 0 = No disponible.     |
| `isSpecialty` | Integer  | 1 = Es especialidad, 0 = Normal.       |
| `createdAt`   | DateTime | Fecha de creación.                     |
| `updatedAt`   | DateTime | Fecha de última actualización.         |

### User (Usuario)

| Campo      | Tipo   | Descripción                        |
| :--------- | :----- | :--------------------------------- |
| `id`       | String | Identificador único.               |
| `username` | String | Nombre de usuario para login.      |
| `password` | String | Contraseña.                        |
| `email`    | String | Correo electrónico.                |
| `name`     | String | Nombre completo.                   |
| `role`     | String | Rol del usuario (ej. USER, ADMIN). |
| `phone`    | String | Número de teléfono.                |
| `address`  | String | Dirección física.                  |
