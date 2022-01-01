package ua.com.alevel.view.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.alevel.config.jpa.DataSourceProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@RequestMapping("/")
public class MainTestController {

    private Statement statement;
    private Connection connection;
    private DataSourceProperties dataSourceProperties;

    public MainTestController(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
        connect();
    }

    @GetMapping
    public String getTest() {

        return String.format("DataSourceProps: url=%1$s, username=%2$s",
                dataSourceProperties.getUrl(),
                dataSourceProperties.getUsername());
    }

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hw_7_data_table",
                    "root",
                    "244268"
            );
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
