CREATE TABLE IF NOT EXISTS product_details
(
    id            INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    fk_product_id INT,
    `name`        VARCHAR(255),
    FOREIGN KEY (fk_product_id) REFERENCES product (product_id) ON DELETE CASCADE ON UPDATE CASCADE
);
