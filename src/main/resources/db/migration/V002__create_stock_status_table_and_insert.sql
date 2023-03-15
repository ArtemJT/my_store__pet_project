CREATE TABLE IF NOT EXISTS stock_status
(
    id       INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `status` VARCHAR(255)
);

INSERT INTO stock_status (id, `status`)
VALUES (1, 'In Stock'),
       (2, 'Out of Stock'),
       (3, 'Pre-Order');

