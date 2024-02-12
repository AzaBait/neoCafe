INSERT INTO users (id, username, password, enabled)
VALUES (1, 'admin', 'adminpass', true);

INSERT INTO user_roles (role_id, user_id) VALUES (1, 1);