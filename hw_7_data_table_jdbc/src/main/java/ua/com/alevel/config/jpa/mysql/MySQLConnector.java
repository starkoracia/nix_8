package ua.com.alevel.config.jpa.mysql;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.DataSourceProperties;
import ua.com.alevel.config.jpa.JpaConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class MySQLConnector implements JpaConnector {

    DataSourceProperties dbProps;
    Connection connection;

    public MySQLConnector(DataSourceProperties dbProps) {
        this.dbProps = dbProps;
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

    @Override
    public Connection getConnection() {
        if(connection == null) {
            connect();
        }
        return connection;
    }

}
