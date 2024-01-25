-- liquibase formatted sql


--changeset happyfxmas:1
create table inventory
(
    id uuid not null,
    quantity bigint not null,
    updated_at timestamp(6) with time zone,
    product_id uuid not null,

    primary key (id),
    unique(product_id)
);

create table product
(
    id uuid not null,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    supplier_id uuid not null,
    description varchar(255) not null,
    title varchar(255),
    type varchar(255) not null,

    primary key (id),
    unique (title)
);

create table supplier
(
    id uuid not null,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    company_name varchar(255),
    email varchar(255),
    phone varchar(255),

    primary key (id),
    unique (company_name),
    unique (email)
    unique (phone)
);

alter table if exists inventory add constraint inventory_product_fk foreign key (product_id) references product;
alter table if exists product add constraint product_supplier_fk foreign key (supplier_id) references supplier;

create index supplier_company_name_idx ON supplier (company_name);
create index product_type_idx ON product (type);
create index product_supplier_id_fkey ON product (supplier_id);
create index inventory_product_id_fkey ON inventory (product_id);
