CREATE TABLE profiles (
                    id SERIAL PRIMARY KEY,
                    name varchar(50) UNIQUE,
                    password varchar(50),
                    role varchar(50) REFERENCES roles(name)
                    );

INSERT INTO profiles (name, password, role)
VALUES ('admin', 'admin', 'admin'), ('Vasia', 'Masha', 'admin');