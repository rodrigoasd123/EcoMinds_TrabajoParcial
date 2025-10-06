-- 1. Primero insertar los roles (tabla padre)
INSERT INTO roles (id, name) VALUES (1, 'USER');
INSERT INTO roles (id, name) VALUES (2, 'ORGANIZADOR');
INSERT INTO roles (id, name) VALUES (3, 'ADMIN');

-- 2. Luego insertar usuarios (tabla padre)
INSERT INTO users(username, password, nombre, apellido, correo, peso_reciclado, puntos) VALUES --admin123
('usuario1','$2a$16$ikuwwKPuKIllmrXxH0yvMO0Iam8arfafoBhVZFNzmjxm86vsF5mye', 'Juan', 'Perez', 'juan@email.com', 15.5, 150);
INSERT INTO users(username, password, nombre, apellido, correo, peso_reciclado, puntos) VALUES --rafael105
('organizador1','$2a$16$5nZXPabxCYWhXNUc2EzaguxjKR4t1jylklB7cVEx1MY7F8MGbSTnK', 'Maria', 'Gonzalez', 'maria@email.com', 0.0, 0);
INSERT INTO users(username, password, nombre, apellido, correo, peso_reciclado, puntos) VALUES --admin123
('admin','$2a$16$ikuwwKPuKIllmrXxH0yvMO0Iam8arfafoBhVZFNzmjxm86vsF5mye', 'Admin', 'Sistema', 'admin@email.com', 0.0, 0);

-- 3. Finalmente la tabla de relación (tabla hija) - usando IDs autogerados
-- Nota: Estos IDs se generarán automáticamente, así que usaremos una aproximación más segura
INSERT INTO user_roles (user_id, role_id) SELECT u.id, 1 FROM users u WHERE u.username = 'usuario1';
INSERT INTO user_roles (user_id, role_id) SELECT u.id, 2 FROM users u WHERE u.username = 'organizador1';  
INSERT INTO user_roles (user_id, role_id) SELECT u.id, 3 FROM users u WHERE u.username = 'admin';

-- 4. Materiales de reciclaje (usando IDENTITY - auto generado)
INSERT INTO material_reciclaje (nombre, descripcion) VALUES ('Plastico PET', 'Botellas de plastico transparente');
INSERT INTO material_reciclaje (nombre, descripcion) VALUES ('Papel', 'Papel y carton limpio');
INSERT INTO material_reciclaje (nombre, descripcion) VALUES ('Vidrio', 'Botellas y frascos de vidrio');
INSERT INTO material_reciclaje (nombre, descripcion) VALUES ('Metal', 'Latas de aluminio y acero');
INSERT INTO material_reciclaje (nombre, descripcion) VALUES ('Electronicos', 'Dispositivos electronicos pequenos');

-- 5. Puntos de acopio en Lima Metropolitana (usando IDENTITY - auto generado)
INSERT INTO punto_acopio (nombre, ubicacion) VALUES ('Centro de Acopio Miraflores', 'Av. Larco 1301, Miraflores, Lima');
INSERT INTO punto_acopio (nombre, ubicacion) VALUES ('EcoPoint San Isidro', 'Av. Javier Prado Este 492, San Isidro, Lima');
INSERT INTO punto_acopio (nombre, ubicacion) VALUES ('Reciclaje La Molina', 'Av. La Universidad 1801, La Molina, Lima');
INSERT INTO punto_acopio (nombre, ubicacion) VALUES ('Verde Surco', 'Av. Primavera 1050, Santiago de Surco, Lima');
INSERT INTO punto_acopio (nombre, ubicacion) VALUES ('EcoCenter Pueblo Libre', 'Av. Brasil 2950, Pueblo Libre, Lima');

-- 6. Recompensas del catalogo (usando IDENTITY - auto generado, con nombre y puntos_requeridos)
INSERT INTO recompensa (nombre, descripcion, requisito, puntos_requeridos) VALUES ('Botella Reutilizable', 'Botella de agua reutilizable de acero inoxidable', '50 puntos de reciclaje', 50);
INSERT INTO recompensa (nombre, descripcion, requisito, puntos_requeridos) VALUES ('Bolsa Ecologica', 'Bolsa ecologica de tela 100% algodon', '30 puntos de reciclaje', 30);
INSERT INTO recompensa (nombre, descripcion, requisito, puntos_requeridos) VALUES ('Planta en Maceta', 'Planta suculenta en maceta biodegradable', '80 puntos de reciclaje', 80);
INSERT INTO recompensa (nombre, descripcion, requisito, puntos_requeridos) VALUES ('Kit de Semillas', 'Kit de semillas organicas para huerto casero', '25 puntos de reciclaje', 25);
INSERT INTO recompensa (nombre, descripcion, requisito, puntos_requeridos) VALUES ('Agenda Reciclada', 'Agenda hecha con papel reciclado', '40 puntos de reciclaje', 40);
INSERT INTO recompensa (nombre, descripcion, requisito, puntos_requeridos) VALUES ('Termo Ecologico', 'Termo termico de bambu y acero inoxidable', '100 puntos de reciclaje', 100);

-- 7. Organizadores de eventos (usando IDENTITY - auto generado)
INSERT INTO organizador (nombre) VALUES ('EcoLima Sostenible');
INSERT INTO organizador (nombre) VALUES ('Verde Metropolitano');
INSERT INTO organizador (nombre) VALUES ('Reciclaje Ciudadano');

-- 8. Eventos de ejemplo (usando subconsultas para obtener IDs de organizadores)
INSERT INTO evento (nombre, fecha, hora, lugar, descripcion, id_organizador) 
SELECT 'Campana de Reciclaje Miraflores', '2025-10-15', '09:00:00', 'Parque Central de Miraflores', 'Gran jornada de reciclaje comunitario con premios ecologicos', o.id_organizador 
FROM organizador o WHERE o.nombre = 'EcoLima Sostenible';

INSERT INTO evento (nombre, fecha, hora, lugar, descripcion, id_organizador) 
SELECT 'Taller de Compostaje', '2025-10-20', '14:00:00', 'Centro Cultural de San Isidro', 'Aprende a hacer compost casero con expertos en sostenibilidad', o.id_organizador 
FROM organizador o WHERE o.nombre = 'Verde Metropolitano';

INSERT INTO evento (nombre, fecha, hora, lugar, descripcion, id_organizador) 
SELECT 'Limpieza de Playa Costa Verde', '2025-10-25', '07:00:00', 'Playa Costa Verde - Miraflores', 'Actividad de limpieza y concientizacion ambiental en la costa limena', o.id_organizador 
FROM organizador o WHERE o.nombre = 'Reciclaje Ciudadano';

INSERT INTO evento (nombre, fecha, hora, lugar, descripcion, id_organizador) 
SELECT 'Feria Ecologica La Molina', '2025-11-05', '10:00:00', 'Plaza Principal de La Molina', 'Feria con productos ecologicos y estaciones de reciclaje', o.id_organizador 
FROM organizador o WHERE o.nombre = 'EcoLima Sostenible';

-- 9. Recolecciones de ejemplo (usando subconsultas para obtener IDs)
INSERT INTO recoleccion (fecha, hora, peso, id_materiales, id_acopio, id_usuario) 
SELECT '2025-10-01', '09:30:00', 2.5, m.id_materiales, p.id_acopio, u.id 
FROM material_reciclaje m, punto_acopio p, users u 
WHERE m.nombre = 'Plastico PET' AND p.nombre = 'Centro de Acopio Miraflores' AND u.username = 'usuario1';

INSERT INTO recoleccion (fecha, hora, peso, id_materiales, id_acopio, id_usuario) 
SELECT '2025-10-02', '14:15:00', 1.8, m.id_materiales, p.id_acopio, u.id 
FROM material_reciclaje m, punto_acopio p, users u 
WHERE m.nombre = 'Papel' AND p.nombre = 'EcoPoint San Isidro' AND u.username = 'usuario1';

INSERT INTO recoleccion (fecha, hora, peso, id_materiales, id_acopio, id_usuario) 
SELECT '2025-10-03', '11:45:00', 3.2, m.id_materiales, p.id_acopio, u.id 
FROM material_reciclaje m, punto_acopio p, users u 
WHERE m.nombre = 'Vidrio' AND p.nombre = 'Reciclaje La Molina' AND u.username = 'usuario1';

-- 10. Canjes de ejemplo (usando subconsultas para obtener IDs)
INSERT INTO canje (fecha, hora, costo, id_recompensa, id_usuario) 
SELECT '2025-10-04', '10:30:00', 30, r.id_recompensa, u.id 
FROM recompensa r, users u 
WHERE r.nombre = 'Bolsa Ecologica' AND u.username = 'usuario1';

INSERT INTO canje (fecha, hora, costo, id_recompensa, id_usuario) 
SELECT '2025-10-04', '15:45:00', 25, r.id_recompensa, u.id 
FROM recompensa r, users u 
WHERE r.nombre = 'Kit de Semillas' AND u.username = 'usuario1';