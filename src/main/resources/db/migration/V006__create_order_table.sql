CREATE TABLE IF NOT EXISTS orders
(
    order_id   INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    amount     DECIMAL(20, 2),
    date_added DATE,
    fk_user_id INT         NOT NULL,
    `status`   VARCHAR(20) NOT NULL,
    FOREIGN KEY (fk_user_id) REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);
