drop schema if exists final_project;

create schema if not exists final_project;

use final_project;

CREATE TABLE product_categories
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    CONSTRAINT pk_product_categories PRIMARY KEY (id)
);

CREATE TABLE clients
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    name           VARCHAR(255)          NULL,
    email          VARCHAR(255)          NULL,
    mobile         VARCHAR(255)          NULL,
    is_supplier    BIT(1)                NULL,
    recommendation VARCHAR(255)          NULL,
    annotation     VARCHAR(255)          NULL,
    CONSTRAINT pk_clients PRIMARY KEY (id)
);

CREATE TABLE employees
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    name     VARCHAR(255)          NULL,
    email    VARCHAR(255)          NULL,
    mobile   VARCHAR(255)          NULL,
    position INT                   NULL,
    CONSTRAINT pk_employees PRIMARY KEY (id)
);

CREATE TABLE job
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    name     VARCHAR(255)          NULL,
    price    VARCHAR(255)          NULL,
    warranty INT                   NULL,
    CONSTRAINT pk_job PRIMARY KEY (id)
);

CREATE TABLE payment_item
(
    id     INT AUTO_INCREMENT NOT NULL,
    name   VARCHAR(255)       NULL,
    income BIT(1)             NULL,
    CONSTRAINT pk_payment_item PRIMARY KEY (id)
);

CREATE TABLE product_materials
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    product_category_id BIGINT                NULL,
    name                VARCHAR(255)          NULL,
    `description`       VARCHAR(255)          NULL,
    code                VARCHAR(255)          NULL,
    vendor_code         VARCHAR(255)          NULL,
    is_warranty         BIT(1)                NULL,
    zero_cost           DECIMAL(20)               NULL,
    repair_cost         DECIMAL(20)               NULL,
    trade_cost          DECIMAL(20)               NULL,
    number_of           INT                   NULL,
    in_stock            BIT(1)                NULL,
    CONSTRAINT pk_product_materials PRIMARY KEY (id)
);

ALTER TABLE product_materials
    ADD CONSTRAINT FK_PRODUCT_MATERIALS_ON_PRODUCT_CATEGORY FOREIGN KEY (product_category_id) REFERENCES product_categories (id);

CREATE TABLE relocatable_products
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    product_material_id BIGINT                NULL,
    number_of           INT                   NULL,
    CONSTRAINT pk_relocatable_products PRIMARY KEY (id)
);

ALTER TABLE relocatable_products
    ADD CONSTRAINT FK_RELOCATABLE_PRODUCTS_ON_PRODUCT_MATERIAL FOREIGN KEY (product_material_id) REFERENCES product_materials (id);

CREATE TABLE job_and_materials
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    name                VARCHAR(255)          NULL,
    price               VARCHAR(255)          NULL,
    warranty_days       INT                   NULL,
    zero_cost           VARCHAR(255)          NULL,
    discount            VARCHAR(255)          NULL,
    doer                BIGINT                NULL,
    comment             VARCHAR(255)          NULL,
    number_of           INT                   NULL,
    is_job              BIT(1)                NULL,
    job_id              BIGINT                NULL,
    product_material_id BIGINT                NULL,
    CONSTRAINT pk_job_and_materials PRIMARY KEY (id)
);

ALTER TABLE job_and_materials
    ADD CONSTRAINT FK_JOB_AND_MATERIALS_ON_DOER FOREIGN KEY (doer) REFERENCES employees (id);

ALTER TABLE job_and_materials
    ADD CONSTRAINT FK_JOB_AND_MATERIALS_ON_JOB FOREIGN KEY (job_id) REFERENCES job (id);

ALTER TABLE job_and_materials
    ADD CONSTRAINT FK_JOB_AND_MATERIALS_ON_PRODUCT_MATERIAL FOREIGN KEY (product_material_id) REFERENCES product_materials (id);

CREATE TABLE orders
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    status          INT                   NOT NULL,
    paid            BIT(1)                NOT NULL,
    client_id       BIGINT                NOT NULL,
    product_type    VARCHAR(255)          NULL,
    brand_name      VARCHAR(255)          NULL,
    model           VARCHAR(255)          NULL,
    malfunction     VARCHAR(255)          NULL,
    appearance      VARCHAR(255)          NULL,
    equipment       VARCHAR(255)          NULL,
    acceptor_note   VARCHAR(255)          NULL,
    estimated_price VARCHAR(255)          NULL,
    quickly         BIT(1)                NOT NULL,
    deadline        datetime              NOT NULL,
    prepayment      DECIMAL(20)               NOT NULL,
    manager_id      BIGINT                NOT NULL,
    doer_id         BIGINT                NULL,
    doer_note       VARCHAR(255)          NULL,
    recommendation  VARCHAR(255)          NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_CLIENT FOREIGN KEY (client_id) REFERENCES clients (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_DOER FOREIGN KEY (doer_id) REFERENCES employees (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_MANAGER FOREIGN KEY (manager_id) REFERENCES employees (id);

CREATE TABLE payments
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    payment_item_id INT                   NOT NULL,
    amount          DECIMAL(20)               NULL,
    income          BIT(1)                NULL,
    date_time       datetime              NULL,
    balance_before  DECIMAL(20)               NULL,
    balance_after   DECIMAL(20)               NULL,
    comment         VARCHAR(255)          NULL,
    employee_id     BIGINT                NOT NULL,
    client_id       BIGINT                NULL,
    order_id        BIGINT                NULL,
    CONSTRAINT pk_payments PRIMARY KEY (id)
);

ALTER TABLE payments
    ADD CONSTRAINT FK_PAYMENTS_ON_CLIENT FOREIGN KEY (client_id) REFERENCES clients (id);

ALTER TABLE payments
    ADD CONSTRAINT FK_PAYMENTS_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employees (id);

ALTER TABLE payments
    ADD CONSTRAINT FK_PAYMENTS_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE payments
    ADD CONSTRAINT FK_PAYMENTS_ON_PAYMENT_ITEM FOREIGN KEY (payment_item_id) REFERENCES payment_item (id);

CREATE TABLE warehouse_posting
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    supplier_id   BIGINT                NOT NULL,
    `description` VARCHAR(255)          NULL,
    employee_id   BIGINT                NOT NULL,
    date_time     datetime              NULL,
    payment_id    BIGINT                NULL,
    CONSTRAINT pk_warehouse_posting PRIMARY KEY (id)
);

ALTER TABLE warehouse_posting
    ADD CONSTRAINT FK_WAREHOUSE_POSTING_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employees (id);

ALTER TABLE warehouse_posting
    ADD CONSTRAINT FK_WAREHOUSE_POSTING_ON_PAYMENT FOREIGN KEY (payment_id) REFERENCES payments (id);

ALTER TABLE warehouse_posting
    ADD CONSTRAINT FK_WAREHOUSE_POSTING_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES clients (id);

CREATE TABLE warehouse_write_off
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    `description` VARCHAR(255)          NULL,
    employee_id   BIGINT                NULL,
    date_time     datetime              NULL,
    CONSTRAINT pk_warehouse_write_off PRIMARY KEY (id)
);

ALTER TABLE warehouse_write_off
    ADD CONSTRAINT FK_WAREHOUSE_WRITE_OFF_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employees (id);

CREATE TABLE IF NOT EXISTS orders_job_and_materials
(
    order_id    BIGINT NOT NULL,
    job_and_materials_id BIGINT NOT NULL,
    CONSTRAINT pk_orders_job_and_materials PRIMARY KEY (order_id, job_and_materials_id),
    CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES orders (id),
    CONSTRAINT fk_job_and_materials_id FOREIGN KEY (job_and_materials_id) REFERENCES job_and_materials (id)
);

CREATE TABLE IF NOT EXISTS warehouse_write_off_relocatable_products
(
    warehouse_write_off_id    BIGINT NOT NULL,
    relocatable_products_id BIGINT NOT NULL,
    CONSTRAINT pk_warehouse_write_off_product_materials PRIMARY KEY (warehouse_write_off_id, relocatable_products_id),
    CONSTRAINT fk_warehouse_write_off_id FOREIGN KEY (warehouse_write_off_id) REFERENCES warehouse_write_off (id),
    CONSTRAINT fk_write_off_relocatable_products_id FOREIGN KEY (relocatable_products_id) REFERENCES relocatable_products (id)
);

CREATE TABLE IF NOT EXISTS warehouse_posting_relocatable_products
(
    warehouse_posting_id    BIGINT NOT NULL,
    relocatable_products_id BIGINT NOT NULL,
    CONSTRAINT pk_warehouse_posting_product_materials PRIMARY KEY (warehouse_posting_id, relocatable_products_id),
    CONSTRAINT fk_warehouse_posting_id FOREIGN KEY (warehouse_posting_id) REFERENCES warehouse_posting (id),
    CONSTRAINT fk_posting_relocatable_products_id FOREIGN KEY (relocatable_products_id) REFERENCES relocatable_products (id)
);