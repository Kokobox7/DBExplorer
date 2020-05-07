CREATE TABLE db_profiles
(
    id          SERIAL PRIMARY KEY,
    host        varchar(50),
    port        integer,
    name        varchar(50) UNIQUE,
    db_name     varchar(50),
    db_user     varchar(50),
    db_password varchar(50)
);

INSERT INTO db_profiles(host, port, name, db_name, db_user, db_password)
VALUES ('dbexploration.postgres.database.azure.com', 5432, 'DBExplorer', 'DBExplorer', 'reader@dbexploration',
        'reader'),
       ('dbexploration.postgres.database.azure.com', 5432, 'demo', 'demo', 'reader@dbexploration', 'reader');
