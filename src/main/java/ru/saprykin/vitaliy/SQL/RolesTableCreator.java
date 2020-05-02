package ru.saprykin.vitaliy.SQL;

public class RolesTableCreator {
    public final String createRoles =
            "CREATE TABLE roles (" +
                    "id SERIAL PRIMARY KEY," +
                    "name char(50) UNIQUE" +
                    ");";

    public final String insertRoles =
            "INSERT INTO roles (name)" +
                    "VALUES ('admin')," +
                    "VALUES ('user');";

}
