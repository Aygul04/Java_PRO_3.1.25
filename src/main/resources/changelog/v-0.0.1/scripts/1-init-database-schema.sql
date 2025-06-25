CREATE TABLE IF NOT EXISTS app_user
(
    id
    BIGSERIAL
    PRIMARY
    KEY,

    username
    varchar
(
    255
) UNIQUE NOT NULL
    );

