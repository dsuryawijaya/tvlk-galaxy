CREATE TABLE IF NOT EXISTS currency (
    id BIGSERIAL PRIMARY KEY,
    from_currency TEXT NOT NULL,
    to_currency TEXT NOT NULL,
    rate NUMERIC(22,15) NOT NULL,
    batch_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by TEXT NOT NULL,
    candidate_id BIGINT,
    fx_currency_pair_id BIGINT
);

CREATE UNIQUE INDEX currency_index ON currency(from_currency, to_currency, batch_id);