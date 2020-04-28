package ru.saprykin.vitaliy.SQL;

public class RolesTableCreator implements TableCreator {
    private final String createRoles =
            "CREATE TABLE roles (" +
            "id SERIAL PRIMARY KEY," +
            "name char(50) UNIQUE" +
            ");";

    private final String insertRoles =
            "INSERT INTO roles (name)" +
            "VALUES ('admin')," +
            "VALUES ('user');";

    @Override
    public void create() {

    }
}
