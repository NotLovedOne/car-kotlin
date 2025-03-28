CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255),
                       password VARCHAR(255),
                       role VARCHAR(50) NOT NULL
);
