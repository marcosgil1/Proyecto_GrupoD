

-- ============================================================
-- SCRIPT COMPLETO BASE DE DATOS - GIMNASIO
-- Autores: Susana Nuñez, Marcos Gil y Bruno Herraez
-- Compatible con MySQL / MariaDB
-- ============================================================

DROP DATABASE IF EXISTS gimnasioGrupoD;
CREATE DATABASE gimnasioGrupoD
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_spanish_ci;
USE gimnasioGrupoD;

-- ============================================================
-- TABLAS PRINCIPALES Y GENERALIZACIONES
-- ============================================================

CREATE TABLE usuario (
    dni VARCHAR(12) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    fecha_alta VARCHAR(10) NOT NULL,
    estado ENUM('activo','inactivo','moroso') NOT NULL,
    contrasena VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE administrador (
    dni_usuario VARCHAR(12) PRIMARY KEY,
    CONSTRAINT fk_administrador_usuario
        FOREIGN KEY (dni_usuario) REFERENCES usuario(dni)
        ON DELETE cascade ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE recepcionista (
    dni_usuario VARCHAR(12) PRIMARY KEY,
    CONSTRAINT fk_recepcionista_usuario
        FOREIGN KEY (dni_usuario) REFERENCES usuario(dni)
        ON DELETE cascade ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE entrenador (
    dni_usuario VARCHAR(12) PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    CONSTRAINT fk_entrenador_usuario
        FOREIGN KEY (dni_usuario) REFERENCES usuario(dni)
        ON DELETE cascade ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE socio (
    dni_usuario VARCHAR(12) PRIMARY KEY,
    CONSTRAINT fk_socio_usuario
        FOREIGN KEY (dni_usuario) REFERENCES usuario(dni)
        ON DELETE cascade ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE full (
    dni_socio VARCHAR(12) PRIMARY KEY,
    cuota_mensual DECIMAL(8,2) NOT NULL,
    CONSTRAINT chk_full_cuota CHECK (cuota_mensual >= 0),
    CONSTRAINT fk_full_socio
        FOREIGN KEY (dni_socio) REFERENCES socio(dni_usuario)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE flexible (
    dni_socio VARCHAR(12) PRIMARY KEY,
    precio_por_actividad DECIMAL(8,2) NOT NULL,
    CONSTRAINT chk_flexible_precio CHECK (precio_por_actividad >= 0),
    CONSTRAINT fk_flexible_socio
        FOREIGN KEY (dni_socio) REFERENCES socio(dni_usuario)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE nomina (
    id_nomina INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    salario_base DECIMAL(10,2) NOT NULL,
    salario_neto DECIMAL(10,2) NOT NULL,
    dni_usuario VARCHAR(12) NOT NULL,
    CONSTRAINT chk_nomina_salario_base CHECK (salario_base >= 0),
    CONSTRAINT chk_nomina_salario_neto CHECK (salario_neto >= 0),
    CONSTRAINT fk_nomina_usuario
        FOREIGN KEY (dni_usuario) REFERENCES usuario(dni)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE actividad (
    id_actividad INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    nivel ENUM('iniciacion','medio','avanzado') NOT NULL
) ENGINE=InnoDB;

CREATE TABLE sala (
    id_sala INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    m_cuadrados DECIMAL(7,2) NOT NULL,
    aforo_max INT NOT NULL,
    CONSTRAINT chk_sala_m_cuadrados CHECK (m_cuadrados > 0),
    CONSTRAINT chk_sala_aforo CHECK (aforo_max > 0)
) ENGINE=InnoDB;

CREATE TABLE actividad_programada (
    id_actividad INT NOT NULL,
    fecha_inicio DATETIME NOT NULL,
    fecha_fin DATETIME NOT NULL,
    dni_entrenador VARCHAR(12) NOT NULL,
    id_sala INT NOT NULL,
    PRIMARY KEY (id_actividad, fecha_inicio),
    CONSTRAINT chk_actividad_programada_fechas CHECK (fecha_fin > fecha_inicio),
    CONSTRAINT fk_actividad_programada_actividad
        FOREIGN KEY (id_actividad) REFERENCES actividad(id_actividad)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_actividad_programada_entrenador
        FOREIGN KEY (dni_entrenador) REFERENCES entrenador(dni_usuario)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_actividad_programada_sala
        FOREIGN KEY (id_sala) REFERENCES sala(id_sala)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Relación Sala - Maquinaria corregida como 1:N.
-- La FK se coloca en maquinaria, que es el lado N.
CREATE TABLE maquinaria (
    id_maquinaria INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(80) NOT NULL,
    marca VARCHAR(80) NOT NULL,
    n_serie VARCHAR(80) NOT NULL UNIQUE,
    estado ENUM('operativa','averiada','mantenimiento') NOT NULL,
    fecha_compra DATE NOT NULL,
    fecha_ultimo_mantenimiento DATE NOT NULL,
    id_sala INT NOT NULL,
    CONSTRAINT fk_maquinaria_sala
        FOREIGN KEY (id_sala) REFERENCES sala(id_sala)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE pago (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    fecha_pago DATE NOT NULL,
    tipo_pago ENUM('mensual','actividad') NOT NULL,
    cantidad DECIMAL(8,2) NOT NULL,
    metodo_pago ENUM('efectivo','tarjeta','bizum','transferencia') NOT NULL,
    CONSTRAINT chk_pago_cantidad CHECK (cantidad > 0)
) ENGINE=InnoDB;

CREATE TABLE pago_mensual (
    id_pago INT PRIMARY KEY,
    dni_socio_full VARCHAR(12) NOT NULL,
    CONSTRAINT fk_pago_mensual_pago
        FOREIGN KEY (id_pago) REFERENCES pago(id_pago)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_pago_mensual_full
        FOREIGN KEY (dni_socio_full) REFERENCES full(dni_socio)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE pago_actividad (
    id_pago INT PRIMARY KEY,
    dni_socio_flexible VARCHAR(12) NOT NULL,
    id_actividad INT NOT NULL,
    fecha_inicio DATETIME NOT NULL,
    CONSTRAINT fk_pago_actividad_pago
        FOREIGN KEY (id_pago) REFERENCES pago(id_pago)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_pago_actividad_flexible
        FOREIGN KEY (dni_socio_flexible) REFERENCES flexible(dni_socio)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_pago_actividad_programada
        FOREIGN KEY (id_actividad, fecha_inicio)
        REFERENCES actividad_programada(id_actividad, fecha_inicio)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE poder_impartir (
    dni_entrenador VARCHAR(12) NOT NULL,
    id_actividad INT NOT NULL,
    PRIMARY KEY (dni_entrenador, id_actividad),
    CONSTRAINT fk_poder_impartir_entrenador
        FOREIGN KEY (dni_entrenador) REFERENCES entrenador(dni_usuario)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_poder_impartir_actividad
        FOREIGN KEY (id_actividad) REFERENCES actividad(id_actividad)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE permitir (
    id_actividad INT NOT NULL,
    id_sala INT NOT NULL,
    PRIMARY KEY (id_actividad, id_sala),
    CONSTRAINT fk_permitir_actividad
        FOREIGN KEY (id_actividad) REFERENCES actividad(id_actividad)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_permitir_sala
        FOREIGN KEY (id_sala) REFERENCES sala(id_sala)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE reservar (
    dni_socio VARCHAR(12) NOT NULL,
    id_actividad INT NOT NULL,
    fecha_inicio DATETIME NOT NULL,
    fecha_reserva DATE NOT NULL,
    PRIMARY KEY (dni_socio, id_actividad, fecha_inicio),
    CONSTRAINT fk_reservar_socio
        FOREIGN KEY (dni_socio) REFERENCES socio(dni_usuario)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_reservar_actividad_programada
        FOREIGN KEY (id_actividad, fecha_inicio)
        REFERENCES actividad_programada(id_actividad, fecha_inicio)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- ============================================================
-- ÍNDICES ADICIONALES
-- ============================================================

CREATE INDEX idx_usuario_estado ON usuario(estado);
CREATE INDEX idx_usuario_fecha_alta ON usuario(fecha_alta);
CREATE INDEX idx_nomina_dni_usuario ON nomina(dni_usuario);
CREATE INDEX idx_actividad_programada_entrenador ON actividad_programada(dni_entrenador);
CREATE INDEX idx_actividad_programada_sala ON actividad_programada(id_sala);
CREATE INDEX idx_actividad_programada_fecha ON actividad_programada(fecha_inicio);
CREATE INDEX idx_maquinaria_estado ON maquinaria(estado);
CREATE INDEX idx_maquinaria_sala ON maquinaria(id_sala);
CREATE INDEX idx_pago_fecha ON pago(fecha_pago);
CREATE INDEX idx_pago_tipo ON pago(tipo_pago);
CREATE INDEX idx_pago_mensual_socio ON pago_mensual(dni_socio_full);
CREATE INDEX idx_pago_actividad_socio ON pago_actividad(dni_socio_flexible);
CREATE INDEX idx_reservar_socio ON reservar(dni_socio);
CREATE INDEX idx_reservar_fecha ON reservar(fecha_reserva);

-- ============================================================
-- DATOS DE PRUEBA
-- ============================================================

INSERT INTO usuario (dni, nombre, apellidos, telefono, email, fecha_alta, estado, contrasena) VALUES
('10000001A', 'Laura', 'Martínez Pérez', '600000001', 'laura.martinez@gimnasio.com', '2024-01-10', 'activo', 'hash_laura'),
('10000002B', 'Carlos', 'Sánchez López', '600000002', 'carlos.sanchez@gimnasio.com', '2024-01-12', 'activo', 'hash_carlos'),
('10000003C', 'Marta', 'Gómez Ruiz', '600000003', 'marta.gomez@gimnasio.com', '2024-01-15', 'activo', 'hash_marta'),
('10000004D', 'Pablo', 'Torres Gil', '600000004', 'pablo.torres@gimnasio.com', '2024-01-18', 'activo', 'hash_pablo'),
('10000005E', 'Nerea', 'Navarro Soler', '600000005', 'nerea.navarro@gimnasio.com', '2024-01-20', 'activo', 'hash_nerea'),
('10000006F', 'Iván', 'Romero Vidal', '600000006', 'ivan.romero@gimnasio.com', '2024-01-22', 'activo', 'hash_ivan'),
('10000007G', 'Ana', 'López Martín', '600000007', 'ana.lopez@email.com', '2024-02-01', 'activo', 'hash_ana'),
('10000008H', 'Jorge', 'Pérez Cano', '600000008', 'jorge.perez@email.com', '2024-02-02', 'activo', 'hash_jorge'),
('10000009J', 'Lucía', 'Hernández Mora', '600000009', 'lucia.hernandez@email.com', '2024-02-03', 'activo', 'hash_lucia'),
('10000010K', 'David', 'García Ramos', '600000010', 'david.garcia@email.com', '2024-02-04', 'activo', 'hash_david'),
('10000011L', 'Paula', 'Jiménez Ferrer', '600000011', 'paula.jimenez@email.com', '2024-02-05', 'activo', 'hash_paula'),
('10000012M', 'Sergio', 'Díaz Molina', '600000012', 'sergio.diaz@email.com', '2024-02-06', 'activo', 'hash_sergio'),
('10000013N', 'Elena', 'Castillo Vega', '600000013', 'elena.castillo@email.com', '2024-02-07', 'activo', 'hash_elena'),
('10000014P', 'Raúl', 'Ortega León', '600000014', 'raul.ortega@email.com', '2024-02-08', 'activo', 'hash_raul'),
('10000015Q', 'Sara', 'Moreno Blanco', '600000015', 'sara.moreno@email.com', '2024-02-09', 'activo', 'hash_sara'),
('10000016R', 'Diego', 'Vargas Santos', '600000016', 'diego.vargas@email.com', '2024-02-10', 'activo', 'hash_diego'),
('10000017S', 'Clara', 'Rojas Campos', '600000017', 'clara.rojas@email.com', '2024-02-11', 'activo', 'hash_clara'),
('10000018T', 'Mario', 'Serrano Cruz', '600000018', 'mario.serrano@email.com', '2024-02-12', 'activo', 'hash_mario'),
('10000019U', 'Alba', 'Reyes Medina', '600000019', 'alba.reyes@email.com', '2024-02-13', 'activo', 'hash_alba'),
('10000020V', 'Hugo', 'Iglesias Nieto', '600000020', 'hugo.iglesias@email.com', '2024-02-14', 'activo', 'hash_hugo'),
('10000021W', 'Irene', 'Marín Pardo', '600000021', 'irene.marin@email.com', '2024-02-15', 'activo', 'hash_irene'),
('10000022X', 'Adrián', 'Fuentes Lara', '600000022', 'adrian.fuentes@email.com', '2024-02-16', 'activo', 'hash_adrian'),
('10000023Y', 'Celia', 'Peña Rubio', '600000023', 'celia.pena@email.com', '2024-02-17', 'activo', 'hash_celia'),
('10000024Z', 'Marcos', 'Gil Herrero', '600000024', 'marcos.gil@email.com', '2024-02-18', 'activo', 'hash_marcos'),
('10000025A', 'Susana', 'Núñez Romero', '600000025', 'susana.nunez@email.com', '2024-02-19', 'activo', 'hash_susana'),
('10000026B', 'Roberto', 'Admin Principal', '600000026', 'roberto.admin@gimnasio.com', '2024-01-05', 'activo', 'hash_roberto'),
('10000027C', 'Carmen', 'Dirección Gimnasio', '600000027', 'carmen.direccion@gimnasio.com', '2024-01-06', 'activo', 'hash_carmen');

INSERT INTO administrador (dni_usuario) VALUES
('10000026B'),
('10000027C');

INSERT INTO recepcionista (dni_usuario) VALUES
('10000001A'),
('10000002B');

INSERT INTO entrenador (dni_usuario, tipo) VALUES
('10000003C', 'Musculación'),
('10000004D', 'Cardio'),
('10000005E', 'Yoga y pilates'),
('10000006F', 'Entrenamiento funcional');

INSERT INTO socio (dni_usuario) VALUES
('10000007G'),
('10000008H'),
('10000009J'),
('10000010K'),
('10000011L'),
('10000012M'),
('10000013N'),
('10000014P'),
('10000015Q'),
('10000016R'),
('10000017S'),
('10000018T'),
('10000019U'),
('10000020V'),
('10000021W'),
('10000022X'),
('10000023Y'),
('10000024Z'),
('10000025A');

INSERT INTO full (dni_socio, cuota_mensual) VALUES
('10000007G', 39.90),
('10000008H', 39.90),
('10000009J', 44.90),
('10000010K', 44.90),
('10000011L', 49.90),
('10000012M', 49.90),
('10000013N', 39.90),
('10000014P', 44.90),
('10000015Q', 49.90),
('10000016R', 39.90);

INSERT INTO flexible (dni_socio, precio_por_actividad) VALUES
('10000017S', 6.50),
('10000018T', 6.50),
('10000019U', 7.00),
('10000020V', 7.00),
('10000021W', 7.50),
('10000022X', 7.50),
('10000023Y', 8.00),
('10000024Z', 8.00),
('10000025A', 7.00);

INSERT INTO nomina (fecha, salario_base, salario_neto, dni_usuario) VALUES
('2025-01-31', 1450.00, 1230.50, '10000001A'),
('2025-01-31', 1450.00, 1230.50, '10000002B'),
('2025-01-31', 1650.00, 1392.75, '10000003C'),
('2025-01-31', 1600.00, 1350.00, '10000004D'),
('2025-01-31', 1580.00, 1334.10, '10000005E'),
('2025-01-31', 1620.00, 1368.90, '10000006F'),
('2025-02-28', 1450.00, 1230.50, '10000001A'),
('2025-02-28', 1450.00, 1230.50, '10000002B'),
('2025-02-28', 1650.00, 1392.75, '10000003C'),
('2025-02-28', 1600.00, 1350.00, '10000004D'),
('2025-02-28', 1580.00, 1334.10, '10000005E'),
('2025-02-28', 1620.00, 1368.90, '10000006F'),
('2025-01-31', 1850.00, 1560.25, '10000026B'),
('2025-01-31', 1800.00, 1522.40, '10000027C'),
('2025-02-28', 1850.00, 1560.25, '10000026B'),
('2025-02-28', 1800.00, 1522.40, '10000027C');

INSERT INTO actividad (nombre, descripcion, nivel) VALUES
('Spinning', 'Clase de bicicleta indoor de alta intensidad', 'medio'),
('Yoga', 'Actividad de relajación, equilibrio y flexibilidad', 'iniciacion'),
('Pilates', 'Ejercicios de control corporal y fortalecimiento del core', 'iniciacion'),
('Cross Training', 'Entrenamiento funcional de fuerza y resistencia', 'avanzado'),
('Zumba', 'Clase cardiovascular basada en baile', 'medio'),
('Body Pump', 'Entrenamiento con barra y música', 'medio'),
('HIIT', 'Entrenamiento interválico de alta intensidad', 'avanzado'),
('GAP', 'Trabajo de glúteos, abdominales y piernas', 'medio'),
('Estiramientos', 'Sesión de movilidad y recuperación muscular', 'iniciacion'),
('Boxeo Fitness', 'Entrenamiento cardiovascular inspirado en boxeo', 'medio'),
('TRX', 'Entrenamiento en suspensión con peso corporal', 'medio'),
('Funcional Senior', 'Actividad funcional adaptada a personas mayores', 'iniciacion');

INSERT INTO sala (nombre, m_cuadrados, aforo_max) VALUES
('Sala Principal', 120.00, 35),
('Sala Cardio', 90.00, 25),
('Sala Yoga', 70.00, 20),
('Sala Funcional', 100.00, 30),
('Sala Spinning', 80.00, 22),
('Sala Musculación', 150.00, 40),
('Sala Pilates', 65.00, 18),
('Sala Boxeo', 85.00, 24),
('Sala Polivalente', 110.00, 32),
('Sala Senior', 60.00, 16);

INSERT INTO actividad_programada (id_actividad, fecha_inicio, fecha_fin, dni_entrenador, id_sala) VALUES
(1, '2025-03-03 09:00:00', '2025-03-03 10:00:00', '10000004D', 5),
(2, '2025-03-03 10:30:00', '2025-03-03 11:30:00', '10000005E', 3),
(3, '2025-03-03 12:00:00', '2025-03-03 13:00:00', '10000005E', 7),
(4, '2025-03-03 17:00:00', '2025-03-03 18:00:00', '10000006F', 4),
(5, '2025-03-03 18:30:00', '2025-03-03 19:30:00', '10000004D', 1),
(6, '2025-03-04 09:00:00', '2025-03-04 10:00:00', '10000003C', 6),
(7, '2025-03-04 10:30:00', '2025-03-04 11:15:00', '10000006F', 4),
(8, '2025-03-04 17:00:00', '2025-03-04 18:00:00', '10000003C', 1),
(9, '2025-03-04 18:30:00', '2025-03-04 19:15:00', '10000005E', 9),
(10, '2025-03-05 09:00:00', '2025-03-05 10:00:00', '10000006F', 8),
(11, '2025-03-05 10:30:00', '2025-03-05 11:30:00', '10000006F', 4),
(12, '2025-03-05 12:00:00', '2025-03-05 13:00:00', '10000005E', 10),
(1, '2025-03-06 09:00:00', '2025-03-06 10:00:00', '10000004D', 5),
(2, '2025-03-06 10:30:00', '2025-03-06 11:30:00', '10000005E', 3),
(4, '2025-03-06 17:00:00', '2025-03-06 18:00:00', '10000006F', 4);

INSERT INTO maquinaria (tipo, marca, n_serie, estado, fecha_compra, fecha_ultimo_mantenimiento, id_sala) VALUES
('Cinta de correr', 'Technogym', 'TG-CIN-001', 'operativa', '2023-01-15', '2025-01-20', 2),
('Bicicleta estática', 'BH Fitness', 'BH-BIC-002', 'operativa', '2023-02-10', '2025-01-22', 2),
('Elíptica', 'Life Fitness', 'LF-ELI-003', 'operativa', '2023-03-05', '2025-01-25', 2),
('Banco press', 'Salter', 'SA-BAN-004', 'operativa', '2022-11-20', '2025-01-18', 6),
('Prensa piernas', 'Matrix', 'MA-PRE-005', 'mantenimiento', '2022-12-12', '2025-02-01', 6),
('Máquina poleas', 'Technogym', 'TG-POL-006', 'operativa', '2023-04-14', '2025-01-28', 6),
('Remo indoor', 'Concept2', 'C2-REM-007', 'operativa', '2023-05-18', '2025-02-02', 4),
('Saco de boxeo', 'Everlast', 'EV-SAC-008', 'operativa', '2023-06-20', '2025-01-30', 8),
('TRX soporte', 'TRX', 'TRX-SOP-009', 'operativa', '2023-07-10', '2025-02-03', 4),
('Mancuernas 1-20kg', 'Ruster', 'RU-MAN-010', 'operativa', '2022-09-01', '2025-01-15', 6),
('Bicicleta spinning', 'Bodytone', 'BT-SPI-011', 'operativa', '2023-08-01', '2025-02-04', 5),
('Bicicleta spinning', 'Bodytone', 'BT-SPI-012', 'operativa', '2023-08-01', '2025-02-04', 5),
('Colchonetas', 'Reebok', 'RB-COL-013', 'operativa', '2024-01-09', '2025-02-05', 3),
('Balones medicinales', 'Ruster', 'RU-BAL-014', 'operativa', '2024-02-11', '2025-02-06', 4),
('Step aeróbico', 'Reebok', 'RB-STE-015', 'averiada', '2023-10-12', '2025-02-10', 1);

INSERT INTO pago (fecha_pago, tipo_pago, cantidad, metodo_pago) VALUES
('2025-03-01', 'mensual', 39.90, 'tarjeta'),
('2025-03-01', 'mensual', 39.90, 'bizum'),
('2025-03-01', 'mensual', 44.90, 'transferencia'),
('2025-03-01', 'mensual', 44.90, 'tarjeta'),
('2025-03-02', 'mensual', 49.90, 'tarjeta'),
('2025-03-02', 'mensual', 49.90, 'efectivo'),
('2025-03-02', 'mensual', 39.90, 'bizum'),
('2025-03-02', 'mensual', 44.90, 'tarjeta'),
('2025-03-03', 'mensual', 49.90, 'transferencia'),
('2025-03-03', 'mensual', 39.90, 'tarjeta'),
('2025-03-03', 'actividad', 6.50, 'tarjeta'),
('2025-03-03', 'actividad', 6.50, 'bizum'),
('2025-03-03', 'actividad', 7.00, 'efectivo'),
('2025-03-04', 'actividad', 7.00, 'tarjeta'),
('2025-03-04', 'actividad', 7.50, 'transferencia'),
('2025-03-04', 'actividad', 7.50, 'bizum'),
('2025-03-04', 'actividad', 8.00, 'tarjeta'),
('2025-03-05', 'actividad', 8.00, 'efectivo'),
('2025-03-05', 'actividad', 7.00, 'tarjeta'),
('2025-03-05', 'actividad', 6.50, 'bizum');

INSERT INTO pago_mensual (id_pago, dni_socio_full) VALUES
(1, '10000007G'),
(2, '10000008H'),
(3, '10000009J'),
(4, '10000010K'),
(5, '10000011L'),
(6, '10000012M'),
(7, '10000013N'),
(8, '10000014P'),
(9, '10000015Q'),
(10, '10000016R');

INSERT INTO pago_actividad (id_pago, dni_socio_flexible, id_actividad, fecha_inicio) VALUES
(11, '10000017S', 1, '2025-03-03 09:00:00'),
(12, '10000018T', 2, '2025-03-03 10:30:00'),
(13, '10000019U', 4, '2025-03-03 17:00:00'),
(14, '10000020V', 5, '2025-03-03 18:30:00'),
(15, '10000021W', 7, '2025-03-04 10:30:00'),
(16, '10000022X', 10, '2025-03-05 09:00:00'),
(17, '10000023Y', 11, '2025-03-05 10:30:00'),
(18, '10000024Z', 12, '2025-03-05 12:00:00'),
(19, '10000025A', 1, '2025-03-06 09:00:00'),
(20, '10000017S', 4, '2025-03-06 17:00:00');

INSERT INTO poder_impartir (dni_entrenador, id_actividad) VALUES
('10000003C', 4),
('10000003C', 6),
('10000003C', 8),
('10000003C', 11),
('10000004D', 1),
('10000004D', 5),
('10000004D', 7),
('10000004D', 10),
('10000005E', 2),
('10000005E', 3),
('10000005E', 9),
('10000005E', 12),
('10000006F', 4),
('10000006F', 7),
('10000006F', 10),
('10000006F', 11);

INSERT INTO permitir (id_actividad, id_sala) VALUES
(1, 5),
(2, 3),
(2, 9),
(3, 7),
(4, 4),
(5, 1),
(6, 6),
(7, 4),
(8, 1),
(9, 9),
(10, 8),
(11, 4),
(12, 10),
(4, 1),
(5, 9);

INSERT INTO reservar (dni_socio, id_actividad, fecha_inicio, fecha_reserva) VALUES
('10000007G', 1, '2025-03-03 09:00:00', '2025-02-27'),
('10000008H', 2, '2025-03-03 10:30:00', '2025-02-27'),
('10000009J', 3, '2025-03-03 12:00:00', '2025-02-28'),
('10000010K', 4, '2025-03-03 17:00:00', '2025-02-28'),
('10000011L', 5, '2025-03-03 18:30:00', '2025-03-01'),
('10000012M', 6, '2025-03-04 09:00:00', '2025-03-01'),
('10000013N', 7, '2025-03-04 10:30:00', '2025-03-01'),
('10000014P', 8, '2025-03-04 17:00:00', '2025-03-02'),
('10000015Q', 9, '2025-03-04 18:30:00', '2025-03-02'),
('10000016R', 10, '2025-03-05 09:00:00', '2025-03-02'),
('10000017S', 1, '2025-03-03 09:00:00', '2025-03-02'),
('10000018T', 2, '2025-03-03 10:30:00', '2025-03-02'),
('10000019U', 4, '2025-03-03 17:00:00', '2025-03-03'),
('10000020V', 5, '2025-03-03 18:30:00', '2025-03-03'),
('10000021W', 7, '2025-03-04 10:30:00', '2025-03-03'),
('10000022X', 10, '2025-03-05 09:00:00', '2025-03-04'),
('10000023Y', 11, '2025-03-05 10:30:00', '2025-03-04'),
('10000024Z', 12, '2025-03-05 12:00:00', '2025-03-04'),
('10000025A', 1, '2025-03-06 09:00:00', '2025-03-05');

-- ============================================================
-- CONSULTAS DE COMPROBACIÓN
-- ============================================================

SELECT 'Base de datos creada correctamente' AS resultado;
SELECT COUNT(*) AS total_usuarios FROM usuario;
SELECT COUNT(*) AS total_administradores FROM administrador;
SELECT COUNT(*) AS total_socios FROM socio;
SELECT COUNT(*) AS total_actividades FROM actividad;
SELECT COUNT(*) AS total_actividades_programadas FROM actividad_programada;
SELECT COUNT(*) AS total_maquinaria FROM maquinaria;
SELECT COUNT(*) AS total_pagos FROM pago;
SELECT COUNT(*) AS total_reservas FROM reservar;


