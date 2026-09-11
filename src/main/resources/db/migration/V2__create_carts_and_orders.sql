CREATE TABLE carts (
 id BIGSERIAL PRIMARY KEY,
 user_id VARCHAR(100) NOT NULL UNIQUE,
 version BIGINT
);
CREATE TABLE cart_items (
 id BIGSERIAL PRIMARY KEY,
 cart_id BIGINT NOT NULL REFERENCES carts(id) ON DELETE CASCADE,
 product_id UUID NOT NULL REFERENCES products(id),
 quantity INTEGER NOT NULL CHECK (quantity > 0),
 UNIQUE(cart_id, product_id)
);
CREATE TABLE orders (
 id BIGSERIAL PRIMARY KEY,
 user_id VARCHAR(100) NOT NULL,
 idempotency_key VARCHAR(100) NOT NULL UNIQUE,
 status VARCHAR(20) NOT NULL,
 total_amount NUMERIC(19,2) NOT NULL,
 created_at TIMESTAMP WITH TIME ZONE NOT NULL
);
CREATE INDEX idx_orders_user ON orders(user_id);
CREATE TABLE order_items (
 id BIGSERIAL PRIMARY KEY,
 order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
 product_id UUID NOT NULL REFERENCES products(id),
 quantity INTEGER NOT NULL CHECK (quantity > 0),
 unit_price NUMERIC(19,2) NOT NULL
);
