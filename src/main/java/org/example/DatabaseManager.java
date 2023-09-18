package org.example;

import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String url = "jdbc:mysql://localhost:3306/OSBBDB";
    private static final String username = "root";
    private static final String password = "Nexushonor30-";

    static {
        Flyway.configure()
                .dataSource(url, username, password)
                .locations("classpath:flyway.scripts")
                .load()
                .migrate();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}