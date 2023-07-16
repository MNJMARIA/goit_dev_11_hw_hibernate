INSERT INTO client(name) VALUES
	('Adel'),
	('Bell'),
	('Eren'),
	('Obi'),
	('Mariia'),
	('Bakugo'),
	('Rihanna'),
	('Hak'),
	('Leo'),
	('Riuu');

INSERT INTO planet (id, name) VALUES
    ('EARTH', 'Earth'),
    ('MARS', 'Mars'),
    ('VENUS', 'Venus'),
    ('MERCURY', 'Mercury'),
    ('JUPITER', 'Jupiter'),
    ('SATURN', 'Saturn'),
    ('URANUS', 'Uranus'),
    ('NEPTUNE', 'Neptune');

INSERT INTO ticket (created_at, client_id, from_planet_id, to_planet_id) VALUES
    (CURRENT_TIMESTAMP, 1, 'EARTH', 'MARS'),
    (CURRENT_TIMESTAMP, 2, 'MARS', 'VENUS'),
    (CURRENT_TIMESTAMP, 3, 'EARTH', 'JUPITER'),
    (CURRENT_TIMESTAMP, 4, 'MERCURY', 'SATURN'),
    (CURRENT_TIMESTAMP, 5, 'VENUS', 'NEPTUNE'),
    (CURRENT_TIMESTAMP, 6, 'MARS', 'URANUS'),
    (CURRENT_TIMESTAMP, 7, 'EARTH', 'MERCURY'),
    (CURRENT_TIMESTAMP, 8, 'JUPITER', 'SATURN'),
    (CURRENT_TIMESTAMP, 9, 'VENUS', 'URANUS'),
    (CURRENT_TIMESTAMP, 10, 'MARS', 'NEPTUNE');