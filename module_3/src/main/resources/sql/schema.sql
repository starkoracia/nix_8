drop schema if exists module_3;

create schema if not exists module_3;

use module_3;

CREATE TABLE if not exists categories
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    name      VARCHAR(255)          NULL,
    is_income BIT(1)                NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE customers
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE account
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255)          NULL,
    balance     DECIMAL(16, 2)        NULL,
    customer_id BIGINT                NULL,
    CONSTRAINT pk_account PRIMARY KEY (id)
);

ALTER TABLE account
    ADD CONSTRAINT FK_ACCOUNT_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customers (id);

CREATE TABLE transactions
(
    id                     BIGINT AUTO_INCREMENT NOT NULL,
    category_id            BIGINT                NULL,
    date_time              datetime              NULL,
    amount                 DECIMAL(16, 2)        NULL,
    account_balance_before DECIMAL(16, 2)        NULL,
    account_balance_after  DECIMAL(16, 2)        NULL,
    account_id             BIGINT                NULL,
    CONSTRAINT pk_transactions PRIMARY KEY (id)
);

ALTER TABLE transactions
    ADD CONSTRAINT FK_TRANSACTIONS_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE transactions
    ADD CONSTRAINT FK_TRANSACTIONS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id);
