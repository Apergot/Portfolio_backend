INSERT INTO users (username, password, enabled, firstname, lastname, email) VALUES ('SilentStorm', '$2a$10$urpm4dBvTyPZrTwimQqGE.HeubQTE2XSTtiW1FE.JsqA5R4sKY3z.', 1, 'Alejandro', 'Perdomo', 'apergot95@hotmail.com.xd');
INSERT INTO users (username, password, enabled, firstname, lastname, email) VALUES ('admin', '$2a$10$urpm4dBvTyPZrTwimQqGE.HeubQTE2XSTtiW1FE.JsqA5R4sKY3z.', 1,'Alejandro', 'Perdomo', 'apergot95@gmail.com');

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users_roles (user_id, roles_id) VALUES (1,1);
INSERT INTO users_roles (user_id, roles_id) VALUES (2,1);
INSERT INTO users_roles (user_id, roles_id) VALUES (2,2);
