-- 1. Primero insertar los roles (tabla padre)
INSERT INTO roles (id, name) VALUES (1, 'USER');
INSERT INTO roles (id, name) VALUES (2, 'ORGANIZADOR');
INSERT INTO roles (id, name) VALUES (3, 'ADMIN');

-- 2. Luego insertar usuarios (tabla padre)
-- Password para usuario1: admin123
INSERT INTO users(username, password, nombre, apellido, correo, peso_reciclado, puntos) VALUES ('usuario1','$2a$16$ikuwwKPuKIllmrXxH0yvMO0Iam8arfafoBhVZFNzmjxm86vsF5mye', 'Juan', 'Perez', 'juan@email.com', 15.5, 150);
-- Password para organizador1: rafael105
INSERT INTO users(username, password, nombre, apellido, correo, peso_reciclado, puntos) VALUES ('organizador1','$2a$16$5nZXPabxCYWhXNUc2EzaguxjKR4t1jylklB7cVEx1MY7F8MGbSTnK', 'Maria', 'Gonzalez', 'maria@email.com', 0.0, 0);
-- Password para admin: admin123
INSERT INTO users(username, password, nombre, apellido, correo, peso_reciclado, puntos) VALUES ('admin','$2a$16$ikuwwKPuKIllmrXxH0yvMO0Iam8arfafoBhVZFNzmjxm86vsF5mye', 'Admin', 'Sistema', 'admin@email.com', 0.0, 0);

-- 3. Finalmente la tabla de relación (tabla hija) - usando IDs autogerados
INSERT INTO user_roles (user_id, role_id) SELECT u.id, 1 FROM users u WHERE u.username = 'usuario1';
INSERT INTO user_roles (user_id, role_id) SELECT u.id, 2 FROM users u WHERE u.username = 'organizador1';
INSERT INTO user_roles (user_id, role_id) SELECT u.id, 3 FROM users u WHERE u.username = 'admin';

-- 4. Materiales de reciclaje
INSERT INTO material_reciclaje (nombre, descripcion) VALUES ('Plastico PET', 'Botellas de plastico transparente');
INSERT INTO material_reciclaje (nombre, descripcion) VALUES ('Papel', 'Papel y carton limpio');
INSERT INTO material_reciclaje (nombre, descripcion) VALUES ('Vidrio', 'Botellas y frascos de vidrio');
INSERT INTO material_reciclaje (nombre, descripcion) VALUES ('Metal', 'Latas de aluminio y acero');
INSERT INTO material_reciclaje (nombre, descripcion) VALUES ('Electronicos', 'Dispositivos electronicos pequenos');

-- 5. Puntos de acopio en Lima Metropolitana
INSERT INTO punto_acopio (nombre, ubicacion) VALUES ('Centro de Acopio Miraflores', 'Av. Larco 1301, Miraflores, Lima');
INSERT INTO punto_acopio (nombre, ubicacion) VALUES ('EcoPoint San Isidro', 'Av. Javier Prado Este 492, San Isidro, Lima');
INSERT INTO punto_acopio (nombre, ubicacion) VALUES ('Reciclaje La Molina', 'Av. La Universidad 1801, La Molina, Lima');
INSERT INTO punto_acopio (nombre, ubicacion) VALUES ('Verde Surco', 'Av. Primavera 1050, Santiago de Surco, Lima');
INSERT INTO punto_acopio (nombre, ubicacion) VALUES ('EcoCenter Pueblo Libre', 'Av. Brasil 2950, Pueblo Libre, Lima');

-- 6. Recompensas del catalogo
INSERT INTO recompensa (nombre, descripcion, requisito, puntos_requeridos) VALUES ('Botella Reutilizable', 'Botella de agua reutilizable de acero inoxidable', '50 puntos de reciclaje', 50);
INSERT INTO recompensa (nombre, descripcion, requisito, puntos_requeridos) VALUES ('Bolsa Ecologica', 'Bolsa ecologica de tela 100% algodon', '30 puntos de reciclaje', 30);
INSERT INTO recompensa (nombre, descripcion, requisito, puntos_requeridos) VALUES ('Planta en Maceta', 'Planta suculenta en maceta biodegradable', '80 puntos de reciclaje', 80);
INSERT INTO recompensa (nombre, descripcion, requisito, puntos_requeridos) VALUES ('Kit de Semillas', 'Kit de semillas organicas para huerto casero', '25 puntos de reciclaje', 25);
INSERT INTO recompensa (nombre, descripcion, requisito, puntos_requeridos) VALUES ('Agenda Reciclada', 'Agenda hecha con papel reciclado', '40 puntos de reciclaje', 40);
INSERT INTO recompensa (nombre, descripcion, requisito, puntos_requeridos) VALUES ('Termo Ecologico', 'Termo termico de bambu y acero inoxidable', '100 puntos de reciclaje', 100);

-- 7. Organizadores de eventos
INSERT INTO organizador (nombre) VALUES ('EcoLima Sostenible');
INSERT INTO organizador (nombre) VALUES ('Verde Metropolitano');
INSERT INTO organizador (nombre) VALUES ('Reciclaje Ciudadano');
