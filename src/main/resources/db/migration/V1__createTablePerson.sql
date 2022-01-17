CREATE TABLE person
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(30) NOT NULL,
    last_name  VARCHAR(30) NOT NULL,
    UNIQUE (first_name, last_name)
);