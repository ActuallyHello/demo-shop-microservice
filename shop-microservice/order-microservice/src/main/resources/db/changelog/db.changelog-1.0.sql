-- liquibase formatted sql


--changeset happyfxmas:1

CREATE TYPE order_status AS ENUM ('CREATED', 'DELIVERED', 'CANCELLED');

create table orders
(
    id uuid not null,
    created_at timestamp(6) with time zone not null,
    updated_at timestamp(6) with time zone not null,
    customer_id uuid not null,
    total_amount numeric(38, 2) not null,
    status order_status not null,

    primary key (id)
);

CREATE TYPE item_status AS ENUM ('IN_STOCK', 'OUT_OF_STOCK', 'LAST');

create table item
(
    id uuid not null,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    product_id uuid not null,
    price numeric(38, 2) not null,
    quantity bigint not null,
    status item_status not null,
    orders_id uuid not null,

    primary key (id)
);

alter table if exists item add constraint item_orders_fk foreign key (orders_id) references orders;

create index orders_updated_at_customer_id_seq ON orders (updated_at, customer_id);
create index orders_status_idx ON orders (status);
create index item_orders_id_fkey ON item (orders_id);

