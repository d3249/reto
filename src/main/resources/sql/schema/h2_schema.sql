create table inventario_vacuna
(
    id          uuid default RANDOM_UUID() primary key,
    marca       varchar(50) not null unique,
    existencias int         not null
);

create table ciudadano
(
    id                 uuid default RANDOM_UUID() primary key,
    cedula             varchar(11)  not null unique,
    nombres            varchar(255) not null,
    apellidos          varchar(255) not null,
    fecha_nacimiento   date         not null,
    correo_electronico varchar(255) not null,
    nivel_enfermedad   varchar(8)   not null
);
