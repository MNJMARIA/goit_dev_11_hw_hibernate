CREATE TABLE IF NOT EXISTS client (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(200) NOT NULL CHECK (LENGTH(name) >= 3 AND LENGTH(name) <= 200)
);

CREATE TABLE IF NOT EXISTS planet (
    id VARCHAR(36) PRIMARY KEY CHECK (REGEXP_LIKE(id, '^[A-Z0-9]+$')),
    name VARCHAR(500) NOT NULL CHECK (LENGTH(name) >= 1 AND LENGTH(name) <= 500)
);

CREATE TABLE IF NOT EXISTS ticket (
    id IDENTITY PRIMARY KEY,
    created_at TIMESTAMP default CURRENT_TIMESTAMP,
    client_id BIGINT,
    from_planet_id VARCHAR(10) NOT NULL,
    to_planet_id VARCHAR(10) NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(id),
    FOREIGN KEY (from_planet_id) REFERENCES planet(id),
    FOREIGN KEY (to_planet_id) REFERENCES planet(id)
);

