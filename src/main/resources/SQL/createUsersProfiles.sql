CREATE TABLE user_profiles
(
    id       SERIAL PRIMARY KEY,
    name     varchar(50) UNIQUE,
    password varchar(50),
    role     varchar(50) REFERENCES user_roles (name)
);

INSERT INTO user_profiles (name, password, role)
VALUES ('admin', 'admin', 'admin'),
       ('Vasia', 'Masha', 'admin');