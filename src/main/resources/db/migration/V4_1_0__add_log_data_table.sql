CREATE TABLE log_data (
    id SERIAL PRIMARY KEY,
    level VARCHAR(10) NOT NULL CHECK (level IN ('INFO', 'DEBUG', 'ERROR')),
    src TEXT,
    message TEXT
);
