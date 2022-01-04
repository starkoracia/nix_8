package ua.com.alevel.config.jpa;

import java.sql.Connection;

public interface JpaConnector {

    Connection getConnection();

}
