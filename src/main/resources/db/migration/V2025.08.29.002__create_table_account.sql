SET search_path TO product;

CREATE TABLE IF NOT EXISTS product
(
    id bigserial PRIMARY KEY,
    name varchar(255) NOT NULL,
    price decimal(10,2) NOT NULL,
    unit varchar(64) NOT NULL
);
