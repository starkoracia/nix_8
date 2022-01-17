package ua.com.alevel.persistence.dao;

public class SQLQueryUtil {

    //    Product SQL Queries    **********************************************

    public static final String PRODUCT_CREATE_SQL_QUERY =
            "insert into products (product_name, price) " +
                    "values ('%1$s', '%2$s');";

    public static final String PRODUCT_UPDATE_SQL_QUERY =
            "update products " +
                    "set product_name = '%1$s', price = '%2$s' " +
                    "where id = '%3$s';";

    public static final String PRODUCT_DELETE_SQL_QUERY =
            "delete from products " +
                    "where id = '%1$s';";

    public static final String PRODUCT_FIND_BY_ID_SQL_QUERY =
            "select * from products " +
                    "where id = '%1$s';";

    public static final String PRODUCT_FIND_ALL_SQL_QUERY =
            "select * from products;";

    public static final String PRODUCT_COUNT_SQL_QUERY =
            "select count(*) as count from products;";

    public static final String PRODUCT_ORDER_DELETE_SQL_QUERY =
            "delete from orders_products " +
                    "where product_id = '%1$s';";


    //    Customer SQL Queries    **********************************************

    public static final String CUSTOMER_CREATE_SQL_QUERY =
            "insert into customers (first_name, last_name, phone_number) " +
                    "values ('%1$s', '%2$s', '%3$s');";

    public static final String CUSTOMER_UPDATE_SQL_QUERY =
            "update customers " +
                    "set first_name = '%1$s', " +
                    "    last_name = '%2$s', " +
                    "    phone_number = '%3$s' " +
                    "where id = '%4$s';";

    public static final String CUSTOMER_DELETE_SQL_QUERY =
            "delete from customers " +
                    "where id = '%1$s';";

    public static final String CUSTOMER_FIND_BY_ID_SQL_QUERY =
            "select * " +
                    "from customers " +
                    "where id = '%1$s'";

    public static final String CUSTOMER_FIND_ALL_SQL_QUERY =
            "select * from customers;";

    public static final String CUSTOMER_COUNT_SQL_QUERY =
            "select count(*) as count from customers;";



    //    Customer SQL Queries    **********************************************


    public static final String ORDER_CREATE_SQL_QUERY =
            "insert into orders (customer_id) " +
                    "values (?);";

    public static final String ORDER_UPDATE_SQL_QUERY =
            "update orders " +
                    "set customer_id = '%1$s' " +
                    "where id = '%2$s';";

    public static final String ORDER_DELETE_SQL_QUERY =
            "delete from orders " +
                    "where id = '%1$s';";

    public static final String ORDER_FIND_BY_ID =
            "select * from orders " +
                    "where id = '%1$s';";

    public static final String ORDER_FIND_ALL_SQL_QUERY =
            "select * from orders;";

    public static final String ORDER_COUNT_SQL_QUERY =
            "select count(*) as count from orders;";

    public static final String ORDER_GET_CUSTOMER_SQL_QUERY =
            "select * from customers " +
                    "where id = (select customer_id from orders where orders.id = '%1$s');";

    public static final String ORDER_GET_ORDERS_FROM_CUSTOMER_SQL_QUERY =
            "select * from orders " +
                    "where customer_id = '%1$s';";

    public static final String ORDER_GET_PRODUCTS_SQL_QUERY =
            "select * from products " +
                    "where id in (select product_id from orders_products where order_id = '%1$s');";

    public static final String ORDER_SET_PRODUCTS_SQL_QUERY =
            "INSERT INTO orders_products (order_id, product_id) " +
                    "VALUES ('%1$s', '%2$s');";

    public static final String ORDER_DELETE_PRODUCTS_SQL_QUERY =
            "delete from orders_products " +
                    "where order_id = '%1$s';";
}
