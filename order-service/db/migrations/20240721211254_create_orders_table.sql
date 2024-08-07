-- +goose Up
-- SQL in section 'Up' is executed when this migration is applied

CREATE TABLE orders (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL
);

-- +goose Down
-- SQL section 'Down' is executed when this migration is rolled back

DROP TABLE orders;
