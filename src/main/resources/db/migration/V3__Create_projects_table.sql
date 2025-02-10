-- Create projects table
CREATE TABLE IF NOT EXISTS task_management_service_schema.projects (
    id BIGSERIAL PRIMARY KEY,  -- Auto-incrementing primary key
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    created_by BIGINT NOT NULL,  -- Foreign key to users table
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Timestamp for when the row is created
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Timestamp for when the row is updated
    CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES task_management_service_schema.users(id)  -- Foreign key constraint
);