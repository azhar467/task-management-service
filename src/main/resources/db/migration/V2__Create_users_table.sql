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

-- Create index on email for faster search queries
CREATE INDEX idx_users_email ON task_management_service_schema.users(email);

-- Create index on date_of_birth for performance optimization when filtering by birth date
CREATE INDEX idx_users_date_of_birth ON task_management_service_schema.users(date_of_birth);

-- Create index on role for filtering users by their role
CREATE INDEX idx_users_role ON task_management_service_schema.users(role);

-- Create index on gender for better performance when filtering by gender
CREATE INDEX idx_users_gender ON task_management_service_schema.users(gender);
