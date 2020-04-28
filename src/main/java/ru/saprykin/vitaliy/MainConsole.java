package ru.saprykin.vitaliy;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainConsole {
    public static void main(String[] args) {
        PrintWriter writer = new PrintWriter(System.out);
        Connection connection = null;
        try {
            connection = DBConnector.connect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            assert connection != null;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT title FROM books");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("title"));
            }
            statement.close();
            DBConnector.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
