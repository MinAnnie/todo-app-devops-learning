CREATE TABLE IF NOT EXISTS tasks(
    title VARCHAR(100),
    description TEXT,
    id serial CONSTRAINT id PRIMARY KEY
);

-- insertar 10 tareas de ejemplo
INSERT INTO tasks(title, description)
VALUES ('Tarea 1', 'Descripción tarea 1'),
       ('Tarea 2', 'Descripción tarea 2'),
       ('Tarea 3', 'Descripción tarea 3'),
       ('Tarea 4', 'Descripción tarea 4'),
       ('Tarea 5', 'Descripción tarea 5'),
       ('Tarea 6', 'Descripción tarea 6'),
       ('Tarea 7', 'Descripción tarea 7'),
       ('Tarea 8', 'Descripción tarea 8'),
       ('Tarea 9', 'Descripción tarea 9'),
       ('Tarea 10', 'Descripción tarea 10')