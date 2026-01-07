--liquibase formatted sql

--changeset madusha_dev:1
-- comment: Creates the users table based on the identity-service's UserTable mapping.
CREATE TABLE users
(
    id            SERIAL PRIMARY KEY,
    first_name    VARCHAR(50)  NOT NULL,
    last_name     VARCHAR(50)  NOT NULL,
    email         VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);