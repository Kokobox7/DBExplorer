CREATE TABLE roles (
                    id SERIAL PRIMARY KEY,
                    name varchar(50) UNIQUE
                    );

INSERT INTO roles (name)
VALUES ('admin'), ('user');



