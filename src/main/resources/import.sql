INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO users(username, password) VALUES ('user1','$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06');
INSERT INTO users(username, password) VALUES ('admin','$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06');
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); -- user1 with ROLE_USER
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2); -- admin with ROLE_ADMIN
INSERT INTO tipo_producto (id, nombre) VALUES (1, 'Ropa')
INSERT INTO tipo_producto (id, nombre) VALUES (2, 'Gasesosa')
INSERT INTO tipo_producto (id, nombre) VALUES (3, 'Mueble')
insert into productos (descripcion, precio, stock, tipo_producto_id) values ('Camisa', 190, 10, 1);