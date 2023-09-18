package org.example;

import java.sql.Connection;
import java.sql.ResultSet;

public class Main {

    public static void main(String[] args) {
        try (Connection connection = DatabaseManager.getConnection()) {
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

            ResultSet resultSet = QueryExecutor.executeQuery(connection, query);

            if (resultSet != null) {
                ResultProcessor.processResultSet(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
