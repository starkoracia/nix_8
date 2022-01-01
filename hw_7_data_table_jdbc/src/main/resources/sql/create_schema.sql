create schema if not exists hw_7_data_table;

create table if not exists `hw_7_data_table`.`customers`
(
    id           BIGINT       NOT NULL,
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NULL,
    phone_number VARCHAR(255) NOT NULL,
    CONSTRAINT pk_customers PRIMARY KEY (id)
);

create table if not exists `hw_7_data_table`.`products`
(
    id           BIGINT       NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    price        DECIMAL      NOT NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);