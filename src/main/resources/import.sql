/* Populate tabla clients */
INSERT INTO regions (id, name) VALUES (1, 'Asia');
INSERT INTO regions (id, name) VALUES (2, 'South America');
INSERT INTO regions (id, name) VALUES (3, 'Europe');
INSERT INTO regions (id, name) VALUES (4, 'Africa');
INSERT INTO regions (id, name) VALUES (5, 'Oceania');
INSERT INTO regions (id, name) VALUES (6, 'Antarctica');
INSERT INTO regions (id, name) VALUES (7, 'North America');
INSERT INTO regions (id, name) VALUES (8, 'Central America');

INSERT INTO certs (name,teacher,cert_id,link,platform,description,image) VALUES ('Vue','Max','132','https://.......','udemy','Esto es una descripción','');

INSERT INTO users (username, password, enabled, firstname, lastname, email) VALUES ('SilentStorm', '$2a$10$urpm4dBvTyPZrTwimQqGE.HeubQTE2XSTtiW1FE.JsqA5R4sKY3z.', 1, 'Alejandro', 'Perdomo', 'someone@mail.xd');
INSERT INTO users (username, password, enabled, firstname, lastname, email) VALUES ('admin', '$2a$10$urpm4dBvTyPZrTwimQqGE.HeubQTE2XSTtiW1FE.JsqA5R4sKY3z.', 1,'Alejandro', 'Perdomo', 'admin@mail.xd');

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users_roles (user_id, roles_id) VALUES (1,1);
INSERT INTO users_roles (user_id, roles_id) VALUES (2,1);
INSERT INTO users_roles (user_id, roles_id) VALUES (2,2);
