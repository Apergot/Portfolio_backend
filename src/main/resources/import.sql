
INSERT INTO certs (name,teacher,cert_id,link,platform,description,image) VALUES ('Vue','Max','132','https://.......','udemy','Esto es una descripción','');

INSERT INTO projects(description,link,name,platform, type) VALUES ('Esto es una descripción','https://github.com/Apergot/Spring-Boot-Restful-API','FrontController','Spring Boot and MySQL',  'Web');

INSERT INTO users (username, password, enabled, firstname, lastname, email) VALUES ('SilentStorm', '$2a$10$urpm4dBvTyPZrTwimQqGE.HeubQTE2XSTtiW1FE.JsqA5R4sKY3z.', 1, 'Alejandro', 'Perdomo', 'someone@mail.xd');
INSERT INTO users (username, password, enabled, firstname, lastname, email) VALUES ('admin', '$2a$10$urpm4dBvTyPZrTwimQqGE.HeubQTE2XSTtiW1FE.JsqA5R4sKY3z.', 1,'Alejandro', 'Perdomo', 'admin@mail.xd');

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users_roles (user_id, roles_id) VALUES (1,1);
INSERT INTO users_roles (user_id, roles_id) VALUES (2,1);
INSERT INTO users_roles (user_id, roles_id) VALUES (2,2);
