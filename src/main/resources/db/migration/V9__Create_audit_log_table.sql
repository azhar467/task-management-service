-- Create audit log table
CREATE TABLE IF NOT EXISTS task_management_service_schema.audit_log (
    id BIGSERIAL PRIMARY KEY,
    request_method VARCHAR(10) NOT NULL,
    request_uri VARCHAR(500) NOT NULL,
    request_payload TEXT,
    response_payload TEXT,
    response_status INTEGER,
    user_agent VARCHAR(500),
    ip_address VARCHAR(45),
    execution_time_ms BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);