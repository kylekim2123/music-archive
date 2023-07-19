DROP TABLE music IF EXISTS;

CREATE TABLE music
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    title            VARCHAR(100) NOT NULL,
    poster_url       VARCHAR(500) NOT NULL,
    description      VARCHAR(500) NOT NULL,
    artist_name      VARCHAR(50)  NOT NULL,
    released_date    DATE         NOT NULL,
    created_datetime DATETIME DEFAULT NULL,
    updated_datetime DATETIME DEFAULT NULL
);