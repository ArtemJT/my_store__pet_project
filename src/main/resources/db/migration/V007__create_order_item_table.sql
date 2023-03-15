CREATE TABLE IF NOT EXISTS order_item
(
    order_item_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `count`       INT,
    cost          DECIMAL(20, 2),
    fk_order_id   INT,
    fk_product_id INT,
    FOREIGN KEY (fk_order_id) REFERENCES orders (order_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (fk_product_id) REFERENCES product (product_id) ON DELETE CASCADE ON UPDATE CASCADE
);
