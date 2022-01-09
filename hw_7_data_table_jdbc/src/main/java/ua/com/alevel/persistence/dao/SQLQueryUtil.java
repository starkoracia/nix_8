package ua.com.alevel.persistence.dao;

public class SQLQueryUtil {

    //    Product SQL Queries    **********************************************

    public static final String PRODUCT_CREATE_SQL_QUERY =
            "insert into products (product_name, price) " +
                    "values ('%1$s', '%2$s')";

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
            "select * " +
                    "from customers;";

    public static final String CUSTOMER_COUNT_SQL_QUERY =
            "select count(*) as count\n" +
                    "from customers;";



}
