-- Create users table
CREATE TABLE IF NOT EXISTS task_management_service_schema.users (
    id BIGSERIAL PRIMARY KEY,  -- Auto-incrementing primary key
    name VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,  -- Unique constraint on email
    role VARCHAR(50) NOT NULL,  -- Enum type for role
    date_of_birth DATE NOT NULL,
    gender VARCHAR(50) NOT NULL,  -- Enum type for gender
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Timestamp for when the row is created
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- Timestamp for when the row is updated
);