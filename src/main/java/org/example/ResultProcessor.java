package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultProcessor {

    public static void processResultSet(ResultSet resultSet) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("result.txt"))) {
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

                String line = fullName + ", " + email + ", " + buildingNumber + ", " + apartmentNumber + ", " + area;
                writer.println(line);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            // Обробка можливих винятків
        }
    }
}
