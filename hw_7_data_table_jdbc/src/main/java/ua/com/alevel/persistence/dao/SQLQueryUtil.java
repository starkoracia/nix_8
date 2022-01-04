package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.entity.Product;

public class SQLQueryUtil {

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


}
