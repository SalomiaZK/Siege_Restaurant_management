package com.example.local.Repository;


import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DatabaseConnection {
    private final String user = System.getenv("USER");
    private final  String password = System.getenv("PASSWORD");
    private final String url = System.getenv("URL") ;

    private Connection connection;



    public DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(url,user , password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public Connection getConnection() {
        return connection;
    }

}

