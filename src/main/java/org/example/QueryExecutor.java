package org.example;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryExecutor {

    private static final Logger logger = Logger.getLogger(QueryExecutor.class.getName());

    public static ResultSet executeQuery(Connection connection, String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("SQLException occurred", e);
            return null;
        }
    }
}
