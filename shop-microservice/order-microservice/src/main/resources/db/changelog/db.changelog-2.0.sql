-- liquibase formatted sql


--changeset happyfxmas:2
create table orders
(
    id uuid not null,
    created_at timestamp(6) with time zone not null,
    updated_at timestamp(6) with time zone not null,
    customer_id uuid not null,
    total_amount numeric(38, 2) not null,
    order_status_id integer not null,

    primary key (id)
);

create table item
(
    id uuid not null,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    product_id uuid not null,
    price numeric(38, 2) not null,
    quantity bigint not null,
    orders_id uuid not null,
    item_status_id integer not null,

    primary key (id)
);

alter table if exists orders add constraint orders_order_item_fk foreign key (order_status_id) references order_status;
alter table if exists item add constraint item_orders_fk foreign key (orders_id) references orders;
alter table if exists item add constraint item_item_status_fk foreign key (item_status_id) references item_status;

create index orders_updated_at_customer_id_seq ON orders (updated_at, customer_id);
create index orders_order_status_id_fkey ON orders (order_status_id);

create index item_orders_id_fkey ON item (orders_id);
create index item_item_status_id_fkey ON item (item_status_id);
