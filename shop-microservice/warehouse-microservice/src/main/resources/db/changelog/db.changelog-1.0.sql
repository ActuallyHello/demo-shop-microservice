-- liquibase formatted sql


--changeset happyfxmas:1
create table inventory
(
    quantity bigint not null,
    updated_at timestamp(6) with time zone,
    id uuid not null,
    product_id uuid not null unique,
    primary key (id)
);

create table product
(
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    id uuid not null,
    supplier_id uuid not null,
    description varchar(255) not null,
    title varchar(255), type varchar(255) not null,
    primary key (id),
    unique (title)
);

create table supplier
(
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    id uuid not null,
    company_name varchar(255),
    email varchar(255),
    phone varchar(255),
    primary key (id),
    unique (company_name)
);

alter table if exists inventory add constraint FKp7gj4l80fx8v0uap3b2crjwp5 foreign key (product_id) references product;

alter table if exists product add constraint FK2kxvbr72tmtscjvyp9yqb12by foreign key (supplier_id) references supplier;
