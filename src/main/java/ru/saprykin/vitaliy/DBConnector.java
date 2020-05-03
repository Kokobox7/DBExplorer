package ru.saprykin.vitaliy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    final private static String DBMSDriver = "org.postgresql.Driver";

    private static Connection appDBConnection;
    private static Connection externalDBConnection;

    public static Connection getExternalDBConnection() throws SQLException {
       return externalDBConnection;
    }

    public static Connection getExternalDBConnection
            (String hostNameORAddress, int port, String dbName, String dbUser, String dbPassword) throws SQLException {
        try {
            Class.forName(DBMSDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        externalDBConnection = DriverManager.getConnection(
                "jdbc:postgresql://" + hostNameORAddress + ":" + port + "/" + dbName,
                dbUser, dbPassword);
        return externalDBConnection;
    }


    public static Connection getAppDBConnection() throws SQLException {
        if (appDBConnection != null) {
            return appDBConnection;
        }
        else return connectToApplicationDB();
    }

    private static Connection connectToApplicationDB() throws SQLException {
        try {
            Class.forName(DBMSDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        appDBConnection = DriverManager.getConnection(
                "jdbc:postgresql://dbexploration.postgres.database.azure.com:5432/" +
                        "DBExplorer?user=reader@dbexploration&password=reader&sslmode=require");

        return appDBConnection;
    }

    public static void close() throws SQLException {
        appDBConnection.close();
    }
}
