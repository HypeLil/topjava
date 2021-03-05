DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO meals (date_time, description, calories, user_id)

 VALUES ('2020-01-01 10:00:00', 'Перекус', 500, 100000),
        ('2020-02-02 13:00:00', 'Укус', 1000, 100001)