# Gestión de vacunación

Link de [Google Classroom](https://classroom.google.com/c/MzE5MDA3NTU3NjQ4)

## API
Una vez levantado el sistema, la documentación se encontrará en las siguientes rutas

* /v3/api-docs (JSON)
* /swagger-ui.html (Swagger)

## Configuración de la base de datos

De manera predeterminada la aplicación arranca en el perfil _qa_.

En este perfil es **necesario** definir las siguentes variables de entorno:

|Variable|Descripción|Ejemplo|
|--------|-----------|-------|
|PG_URL  | URL de conexión a una base de datos PostgreSQL | jdbc:postgresql://localhost:5432/esquema |
|PG_USERNAME | Nombre de usuario para la conexión a la base de datos  | usuario |
|PG_PASSWORD | Contraseña para la conexión a la base de datos  | Password.123 |
|ADMIN | Nombre de usuario para acceder a las partes restringidas del sistema  | Admin |
|PASSWORD | Contraseña del usuario para acceder a las partes restringidas del sistema  | Password.123 |

La instancia de PostgreSQL debe tener habilitada la extensión _pgcrypto_.

Alternativamente se puede activar el perfil _dev_ que usa una base de datos embebida (H2). 
En este caso el usuario y contraseña del administrador del sistema serán _admin_ y _password_ respectivamente.
