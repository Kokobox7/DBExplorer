CREATE TABLE user_roles
(
    id   SERIAL PRIMARY KEY,
    name varchar(50) UNIQUE
);

INSERT INTO user_roles (name)
VALUES ('admin'),
       ('user');



