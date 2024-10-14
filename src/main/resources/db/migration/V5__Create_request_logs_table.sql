CREATE TABLE request_logs (
  id SERIAL PRIMARY KEY,
  method VARCHAR(10),
  endpoint VARCHAR(255),
  headers JSONB,
  query_params JSONB,
  request_body JSONB,
  timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
