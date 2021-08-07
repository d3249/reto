drop table if exists inventario_vacuna cascade;
create table inventario_vacuna
(
    id          uuid  primary key default gen_random_uuid(),
    marca       varchar(50) not null unique,
    existencias int         not null
);