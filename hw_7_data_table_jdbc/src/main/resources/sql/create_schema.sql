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

create table if not exists `hw_7_data_table`.`orders`
(
    id          BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id),
    CONSTRAINT FK_ORDERS_ON_CUSTOMER FOREIGN KEY (customer_id)
        REFERENCES customers (id)
);

create table if not exists `hw_7_data_table`.`orders_products`
(
    order_id    BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT pk_orders_products PRIMARY KEY (order_id, products_id),
    CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES orders (id),
    CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products (id)
);

