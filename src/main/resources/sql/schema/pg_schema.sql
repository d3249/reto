drop table if exists inventario_vacuna cascade;
create table inventario_vacuna
(
    id          uuid primary key default gen_random_uuid(),
    marca       varchar(50) not null unique,
    existencias int         not null
);

drop table if exists ciudadano cascade;
create table ciudadano
(
    id                 uuid primary key default gen_random_uuid(),
    cedula             varchar(11)  not null unique,
    nombres            varchar(255) not null,
    apellidos          varchar(255) not null,
    fecha_nacimiento   date         not null,
    correo_electronico varchar(255) not null,
    nivel_enfermedad   varchar(8)   not null,
    estatus            varchar(10)  not null,
    fecha_registro     timestamp    not null
);

drop table if exists cita cascade;
create table cita
(
    id           uuid  primary key default gen_random_uuid(),
    fk_ciudadano uuid references ciudadano (id) not null,
    fecha        date                           not null,
    marca        varchar(50)
)
