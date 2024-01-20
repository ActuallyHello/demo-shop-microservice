-- liquibase formatted sql


--changeset happyfxmas:1
create table order_status
(
    id integer not null,
    code varchar(255) not null,
    primary key (id),
    unique (code)
);

create table item_status
(
    id integer not null,
    code varchar(255) not null,
    primary key (id),
    unique (code)
);
