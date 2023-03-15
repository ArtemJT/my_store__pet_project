CREATE TABLE IF NOT EXISTS category
(
    id      INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`  VARCHAR(255),
    measure VARCHAR(120)
);

INSERT INTO category (id, `name`, measure)
VALUES (1, 'LED Strip', 'm'),
       (2, 'Power Supply', 'pcs'),
       (3, 'Lamps', 'pcs');
