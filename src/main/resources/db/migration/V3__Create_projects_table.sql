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

-- Create index on created_by to optimize queries filtering by project creator
CREATE INDEX idx_projects_created_by ON task_management_service_schema.projects(created_by);

-- Create index on start_date to optimize queries filtering by project start date
CREATE INDEX idx_projects_start_date ON task_management_service_schema.projects(start_date);

-- Create index on end_date to optimize queries filtering by project end date
CREATE INDEX idx_projects_end_date ON task_management_service_schema.projects(end_date);
