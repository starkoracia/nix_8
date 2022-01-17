package ua.com.alevel.config.jdbc.mysql;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jdbc.DataSourceProperties;
import ua.com.alevel.config.jdbc.JdbcConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class MySQLConnector implements JdbcConnector {

    DataSourceProperties dbProps;
    Connection connection;

    public MySQLConnector(DataSourceProperties dbProps) {
        this.dbProps = dbProps;
    }

    @Override
    public Connection getConnection() {
        if(connection == null) {
            connect();
        }
        return connection;
    }

    private void connect() {
        try {
            Class.forName(dbProps.getDriverClassName());
            connection = DriverManager.getConnection(
                    dbProps.getUrl(),
                    dbProps.getUsername(),
                    dbProps.getPassword()
            );
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
