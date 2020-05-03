CREATE TABLE db_profiles
(
    id                SERIAL PRIMARY KEY,
    hostNameORAddress varchar(50),
    port              integer,
    dbName            varchar(50) UNIQUE,
    dbUser            varchar(50),
    dbPassword        varchar(50)
);

INSERT INTO db_profiles(hostNameORAddress, port, dbName, dbUser, dbPassword)
VALUES ('dbexploration.postgres.database.azure.com', 5432, 'DBExplorer', 'reader@dbexploration', 'reader'),
       ('dbexploration.postgres.database.azure.com', 5432, 'demo', 'reader@dbexploration', 'reader');


/*final private static String hostNameORAddress = "localhost";
final private static String port = "5432";
final private static String dbName = "test_db";
final private static String dbUser = "postgres";
final private static String dbPassword = "admin";*/