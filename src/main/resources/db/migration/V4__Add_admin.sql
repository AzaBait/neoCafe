INSERT INTO users (id, username, password, enabled)
VALUES (nextVal('hibernate_sequence'), 'admin', 'adminpass', true);