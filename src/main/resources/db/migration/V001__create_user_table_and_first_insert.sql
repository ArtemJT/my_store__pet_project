CREATE TABLE IF NOT EXISTS users
(
    user_id    INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    date_added DATETIME,
    `name`     VARCHAR(255) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    email      VARCHAR(120) NOT NULL,
    `role`     VARCHAR(20)  NOT NULL,
    `status`   VARCHAR(20)  NOT NULL
);

INSERT INTO users (date_added, `name`, `password`, email, `role`, `status`)
VALUES ('2023-01-01 01:01:01',
        'admin',
        '$2a$10$bIUD5UskhHw/XoNz28sLKeJqsdkQuv5Lo00NaDnwvXhmLzUXA3iwq',
        'admin@admin',
        'ADMIN',
        'ADMIN'),
-- login: admin     password: admin
       ('2023-02-02 02:02:02',
        'user',
        '$2a$10$jW6Gb3xaV0Mfqkbu3/3cHuG.CT9z5Ksi748zC7la5PrTIKxSHH/0K',
        'user@user',
        'USER',
        'UNBLOCKED');
-- login: user     password: user
