package com.example.local.Repository;

import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DatabaseConnection {
    private final String user;
    private final String password;
    private final String url;
    private final Connection connection;

    public DatabaseConnection() {
        this.user = System.getenv("USER");
        this.password = System.getenv("PASSWORD");
        this.url = System.getenv("URL");

        if (user == null || password == null || url == null) {
            throw new IllegalStateException("Database configuration environment variables not set");
        }

        try {
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create database connection", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}