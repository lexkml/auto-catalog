CREATE TABLE car
(
    id        BIGSERIAL PRIMARY KEY,
    model     VARCHAR(40) NOT NULL,
    year      VARCHAR(4)  NOT NULL,
    color     VARCHAR(20) NOT NULL,
    person_id BIGINT REFERENCES catalog.person (id)
);