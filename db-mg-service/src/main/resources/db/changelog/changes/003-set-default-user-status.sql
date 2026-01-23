--liquibase formatted sql

--changeset madusha_dev:3
-- comment: Set default value for status_id in users table to 1 (Active)
ALTER TABLE users ALTER COLUMN status_id SET DEFAULT 1;
