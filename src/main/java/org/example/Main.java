package org.example;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.flywaydb.core.Flyway;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static String url = "jdbc:mysql://localhost:3306/OSBBDB";
    public static String username = "root";
    public static String password = "Nexushonor30-";

    static {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
    }

    public static void main(String[] args) {
        Connection connection = null;

        logger.info("The program has started");
        logger.debug("Flyway migration execute");

        Flyway.configure()
                .dataSource(url, username, password)
                .locations("classpath:flyway.scripts")
                .load()
                .migrate();

        try {
            connection = DriverManager.getConnection(url, username, password);

            String query = "SELECT " +
                    "OSBBMembers.full_name, " +
                    "OSBBMembers.email, " +
                    "Buildings.building_number, " +
                    "Apartments.apartment_number, " +
                    "Apartments.area " +
                    "FROM OSBBMembers " +
                    "JOIN Ownership ON OSBBMembers.member_id = Ownership.member_id " +
                    "JOIN Apartments ON Ownership.apartment_id = Apartments.apartment_id " +
                    "JOIN Buildings ON Apartments.building_id = Buildings.building_id " +
                    "LEFT JOIN Residents ON OSBBMembers.member_id = Residents.member_id " +
                    "WHERE Residents.car_access = 0 " +
                    "AND OSBBMembers.member_id IN ( " +
                    "   SELECT Ownership.member_id " +
                    "   FROM Ownership " +
                    "   GROUP BY Ownership.member_id " +
                    "   HAVING COUNT(Ownership.apartment_id) < 2 " +
                    ")";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String fullName = resultSet.getString("full_name");
                String email = resultSet.getString("email");
                int buildingNumber = resultSet.getInt("building_number");
                int apartmentNumber = resultSet.getInt("apartment_number");
                double area = resultSet.getDouble("area");

                System.out.println("Full Name: " + fullName);
                System.out.println("Email: " + email);
                System.out.println("Building Number: " + buildingNumber);
                System.out.println("Apartment Number: " + apartmentNumber);
                System.out.println("Area: " + area);
                System.out.println();
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter("result.txt"))) {
                resultSet.beforeFirst();

                while (resultSet.next()) {
                    String fullName = resultSet.getString("full_name");
                    String email = resultSet.getString("email");
                    int buildingNumber = resultSet.getInt("building_number");
                    int apartmentNumber = resultSet.getInt("apartment_number");
                    double area = resultSet.getDouble("area");

                    String line = fullName + ", " + email + ", " + buildingNumber + ", " + apartmentNumber + ", " + area;
                    writer.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("IOException occurred while writing to file", e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("SQLException occurred", e);
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("SQLException occurred while closing connection", e);
            }
        }
    }
}