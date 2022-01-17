package ua.com.alevel.config.jdbc;

import java.sql.Connection;

public interface JdbcConnector {

    Connection getConnection();

}
