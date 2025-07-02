CREATE TABLE product
(
    id             BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(255) UNIQUE NOT NULL,
    balance        DECIMAL(19, 2)      NOT NULL,
    product_type   VARCHAR(50)         NOT NULL,
    user_id        BIGINT              NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES app_user (id)
);

