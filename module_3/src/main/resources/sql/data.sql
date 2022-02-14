INSERT INTO categories (name) VALUES ('Зарплата');
INSERT INTO categories (name) VALUES ('Донат');
INSERT INTO categories (name) VALUES ('Убийство чудищ');
INSERT INTO categories (name) VALUES ('Питание');
INSERT INTO categories (name) VALUES ('Кварплата');
INSERT INTO categories (name) VALUES ('Отдых');
INSERT INTO categories (name) VALUES ('Охота на карпа');
INSERT INTO categories (name) VALUES ('Разное');

INSERT INTO customers (name) VALUES ('Геральд Ривийский');
INSERT INTO customers (name) VALUES ('Довакин Фусрота');
INSERT INTO customers (name) VALUES ('Альбус Дамблдор');
INSERT INTO customers (name) VALUES ('Дин Джарин');
INSERT INTO customers (name) VALUES ('Тан Сан');
INSERT INTO customers (name) VALUES ('Жак Фреско');
INSERT INTO customers (name) VALUES ('Денис Исаев');

INSERT INTO account (name, balance, customer_id) VALUES ('1-1', 1000.00, 1);
INSERT INTO account (name, balance, customer_id) VALUES ('2-2', 9999999.00, 2);
INSERT INTO account (name, balance, customer_id) VALUES ('3-3', 100000.00, 3);
INSERT INTO account (name, balance, customer_id) VALUES ('4-4', 11500.00, 4);
INSERT INTO account (name, balance, customer_id) VALUES ('5-5', 1105000.00, 5);
INSERT INTO account (name, balance, customer_id) VALUES ('6-6', 50000.00, 6);
INSERT INTO account (name, balance, customer_id) VALUES ('7-7', 5000.00, 7);

INSERT INTO transactions (category_id, date_time, amount, account_balance_before, account_balance_after, account_id)
VALUES (3, '2022-02-09 14:18:11', 4000.00, 1000.00, 5000.00, 1);
INSERT INTO transactions (category_id, date_time, amount, account_balance_before, account_balance_after, account_id)
VALUES (8, '2022-02-09 14:18:11', -4000.00, 9999999.00, 9995999.00, 2);
INSERT INTO transactions (category_id, date_time, amount, account_balance_before, account_balance_after, account_id)
VALUES (1, '2022-02-09 14:29:22', 35500.00, 11500.00, 47000.00, 4);
INSERT INTO transactions (category_id, date_time, amount, account_balance_before, account_balance_after, account_id)
VALUES (7, '2022-02-09 14:29:22', -35500.00, 1105000.00, 1069500.00, 5);

