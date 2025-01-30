DELETE FROM meals;
DELETE FROM user_role;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories)
VALUES (100000, '2025-01-18 07:00:00', 'Завтрак', 500),
       (100000, '2025-01-18 10:00:00', 'Обед', 1000),
       (100000, '2025-01-18 17:00:00', 'Ужин', 501),
       (100000, '2025-01-19 07:00:00', 'Завтрак', 500),
       (100000, '2025-01-19 10:00:00', 'Обед', 1000),
       (100001, '2025-01-18 07:00:00', 'Завтрак', 600),
       (100001, '2025-01-18 12:00:00', 'Перекус', 100);
