--liquibase formatted sql

--changeset madusha_dev:2
-- comment: Creates the user_status table and adds status_id to users table.
CREATE TABLE user_status
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO user_status (name) VALUES ('Active');
INSERT INTO user_status (name) VALUES ('Inactive');
INSERT INTO user_status (name) VALUES ('Logout');
INSERT INTO user_status (name) VALUES ('Banned');

ALTER TABLE users ADD COLUMN status_id INT;
ALTER TABLE users ADD CONSTRAINT fk_user_status FOREIGN KEY (status_id) REFERENCES user_status (id);

-- Set default status to 'Active' for existing users
UPDATE users SET status_id = (SELECT id FROM user_status WHERE name = 'Active') WHERE status_id IS NULL;

-- Make status_id NOT NULL after setting defaults
ALTER TABLE users ALTER COLUMN status_id SET NOT NULL;
