CREATE TABLE table_filters
(
    id               SERIAL PRIMARY KEY,
    name             varchar(50),
    db_name          varchar(50) REFERENCES db_profiles (name),
    schema           varchar(50),
    filtered_table   varchar(50),
    filtered_columns varchar(50)[]
);

INSERT INTO table_filters (name, db_name, schema, filtered_table, filtered_columns)
VALUES ('filters', 'DBExplorer', 'public', 'table_filters', '{name, filtered_table, filtered_columns}'),
       ('login-password', 'DBExplorer', 'public', 'user_profiles', '{name, password}'),
       ('airports', 'demo', 'bookings', 'airports', '{airport_name, city}');