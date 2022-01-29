CREATE TABLE products
(
    id      BIGSERIAL PRIMARY KEY,
    name    TEXT        NOT NULL,
    price   INT         NOT NULL CHECK ( price >= 0 ),
    qty     INT         NOT NULL CHECK ( qty >= 0 ) DEFAULT 0,
    image   TEXT        NOT NULL,
    removed BOOL        NOT NULL                    DEFAULT FALSE,
    created timestamptz NOT NULL                    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sales
(
    id         BIGSERIAL PRIMARY KEY,
    product_id BIGINT      NOT NULL REFERENCES products,
    name       TEXT        NOT NULL,
    price      INT         NOT NULL CHECK ( price >= 0 ),
    qty        INT         NOT NULL CHECK ( qty > 0 ) DEFAULT 1,
    created    timestamptz NOT NULL                   DEFAULT CURRENT_TIMESTAMP
);
