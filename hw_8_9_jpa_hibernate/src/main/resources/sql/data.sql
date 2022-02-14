insert into products (product_name, price) values ('Телевизор', '19000');
insert into products (product_name, price) values ('Диван', '10200');
insert into products (product_name, price) values ('Ручка', '10.50');
insert into products (product_name, price) values ('Кондиционер', '7500');
insert into products (product_name, price) values ('Ботинки', '2500');
insert into products (product_name, price) values ('Куртка', '3300');
insert into products (product_name, price) values ('Шапка', '380');
insert into products (product_name, price) values ('Брюки', '777');
insert into products (product_name, price) values ('Топор', '99999');
insert into products (product_name, price) values ('Латы', '99999');
insert into products (product_name, price) values ('Шлем', '99999');


insert into customers (first_name, last_name, phone_number)
values ('Денис', 'Исаев', '380984252103');
insert into customers (first_name, last_name, phone_number)
values ('Тарас', 'Распорня', '380975877543');
insert into customers (first_name, last_name, phone_number)
values ('Дмитрий', 'Иванов', '380970077333');
insert into customers (first_name, last_name, phone_number)
values ('Екатерина', 'Гайдук', '380670000000');
insert into customers (first_name, last_name, phone_number)
values ('Ривийский', 'Геральд', '380980052552');
insert into customers (first_name, last_name, phone_number)
values ('Фусрота', 'Довакин', '380971112233');

insert into orders (customer_id) values (1);
insert into orders (customer_id) values (6);
insert into orders (customer_id) values (5);

insert into orders_products (order_id, product_id) values ('1', '5');
insert into orders_products (order_id, product_id) values ('1', '6');
insert into orders_products (order_id, product_id) values ('1', '7');
insert into orders_products (order_id, product_id) values ('1', '8');
insert into orders_products (order_id, product_id) values ('2', '9');
insert into orders_products (order_id, product_id) values ('2', '10');
insert into orders_products (order_id, product_id) values ('2', '11');