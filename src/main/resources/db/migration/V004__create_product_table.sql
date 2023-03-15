CREATE TABLE IF NOT EXISTS product
(
    product_id         INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    model              VARCHAR(255),
    price              DECIMAL(10, 2),
    fk_stock_status_id INT,
    fk_category_id     INT,
    date_added         DATE,
    image              TEXT,
    FOREIGN KEY (fk_stock_status_id) REFERENCES stock_status (id),
    FOREIGN KEY (fk_category_id) REFERENCES category (id)
);