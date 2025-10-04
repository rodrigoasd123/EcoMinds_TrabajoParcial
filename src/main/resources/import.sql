-- 1. Primero insertar los roles (tabla padre)
INSERT INTO roles (id, name) VALUES (1, 'USER');
INSERT INTO roles (id, name) VALUES (2, 'ORGANIZADOR');
INSERT INTO roles (id, name) VALUES (3, 'ADMIN');

-- 2. Luego insertar usuarios (tabla padre)
INSERT INTO users(id, username, password, nombre, apellido, correo, peso_reciclado, puntos) VALUES 
(1, 'usuario1','$2a$16$Pf0do24nTZKdQhBzIRZ.Eu20UAA7UWshh5/Ca.YOkY7Ne7bkntPVy', 'Juan', 'Pérez', 'juan@email.com', 15.5, 150);
INSERT INTO users(id, username, password, nombre, apellido, correo, peso_reciclado, puntos) VALUES 
(2, 'organizador1','$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06', 'María', 'González', 'maria@email.com', 0.0, 0);
INSERT INTO users(id, username, password, nombre, apellido, correo, peso_reciclado, puntos) VALUES 
(3, 'admin','$2a$16$Pf0do24nTZKdQhBzIRZ.Eu20UAA7UWshh5/Ca.YOkY7Ne7bkntPVy', 'Admin', 'Sistema', 'admin@email.com', 0.0, 0);

-- 3. Finalmente la tabla de relación (tabla hija)
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); -- usuario1 with USER
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2); -- organizador1 with ORGANIZADOR  
INSERT INTO user_roles (user_id, role_id) VALUES (3, 3); -- admin with ADMIN

-- 4. Materiales de reciclaje (usando IDENTITY - auto generado)
INSERT INTO material_reciclaje (nombre, descripcion) VALUES ('Plástico PET', 'Botellas de plástico transparente');
INSERT INTO material_reciclaje (nombre, descripcion) VALUES ('Papel', 'Papel y cartón limpio');
INSERT INTO material_reciclaje (nombre, descripcion) VALUES ('Vidrio', 'Botellas y frascos de vidrio');
INSERT INTO material_reciclaje (nombre, descripcion) VALUES ('Metal', 'Latas de aluminio y acero');
INSERT INTO material_reciclaje (nombre, descripcion) VALUES ('Electrónicos', 'Dispositivos electrónicos pequeños');

-- 5. Puntos de acopio en Lima Metropolitana (usando IDENTITY - auto generado)
INSERT INTO punto_acopio (nombre, ubicacion) VALUES ('Centro de Acopio Miraflores', 'Av. Larco 1301, Miraflores, Lima');
INSERT INTO punto_acopio (nombre, ubicacion) VALUES ('EcoPoint San Isidro', 'Av. Javier Prado Este 492, San Isidro, Lima');
INSERT INTO punto_acopio (nombre, ubicacion) VALUES ('Reciclaje La Molina', 'Av. La Universidad 1801, La Molina, Lima');
INSERT INTO punto_acopio (nombre, ubicacion) VALUES ('Verde Surco', 'Av. Primavera 1050, Santiago de Surco, Lima');
INSERT INTO punto_acopio (nombre, ubicacion) VALUES ('EcoCenter Pueblo Libre', 'Av. Brasil 2950, Pueblo Libre, Lima');

-- 6. Recompensas del catálogo (usando IDENTITY - auto generado, requisito como String)
INSERT INTO recompensa (descripcion, requisito) VALUES ('Botella de agua reutilizable', '50');
INSERT INTO recompensa (descripcion, requisito) VALUES ('Bolsa ecológica de tela', '30');
INSERT INTO recompensa (descripcion, requisito) VALUES ('Planta en maceta pequeña', '80');
INSERT INTO recompensa (descripcion, requisito) VALUES ('Kit de semillas orgánicas', '25');
INSERT INTO recompensa (descripcion, requisito) VALUES ('Agenda reciclada', '40');
INSERT INTO recompensa (descripcion, requisito) VALUES ('Termo ecológico', '100');

-- 7. Organizadores de eventos (usando IDENTITY - auto generado)
INSERT INTO organizador (nombre) VALUES ('EcoLima Sostenible');
INSERT INTO organizador (nombre) VALUES ('Verde Metropolitano');
INSERT INTO organizador (nombre) VALUES ('Reciclaje Ciudadano');

-- Nota: Los eventos, recolecciones y canjes se crearán a través de la API
-- ya que dependen de IDs auto-generados de otras tablas

-- Datos del sistema anterior (mantenidos para compatibilidad)
INSERT INTO tipo_producto (id, nombre) VALUES (1, 'Ropa');
INSERT INTO tipo_producto (id, nombre) VALUES (2, 'Gaseosa');
INSERT INTO tipo_producto (id, nombre) VALUES (3, 'Mueble');
INSERT INTO productos (descripcion, precio, stock, tipo_producto_id) VALUES ('Camisa', 190, 10, 1);