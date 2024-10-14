-- Create tasks table
CREATE TABLE IF NOT EXISTS task_management_service_schema.tasks (
    id BIGSERIAL PRIMARY KEY,  -- Auto-incrementing primary key
    title VARCHAR(200) NOT NULL,
    description VARCHAR(1000),
    status VARCHAR(50) NOT NULL,  -- Enum type for task status
    priority VARCHAR(50) NOT NULL,  -- Enum type for task priority
    assignee_id BIGINT NOT NULL,  -- Foreign key referencing users table (assignee)
    project_id BIGINT NOT NULL,  -- Foreign key referencing projects table (project)
    due_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Timestamp for when the row is created
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Timestamp for when the row is updated
    CONSTRAINT fk_assignee FOREIGN KEY (assignee_id) REFERENCES task_management_service_schema.users(id),  -- Foreign key for assignee
    CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES task_management_service_schema.projects(id)  -- Foreign key for project
);

-- Create trigger function to update updated_at timestamp on row update
CREATE OR REPLACE FUNCTION update_task_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create trigger on the tasks table to call the update function before each row update
CREATE TRIGGER set_task_updated_at
BEFORE UPDATE ON task_management_service_schema.tasks
FOR EACH ROW
EXECUTE FUNCTION update_task_updated_at_column();

-- Create index on assignee_id to optimize queries filtering by task assignee
CREATE INDEX idx_tasks_assignee_id ON task_management_service_schema.tasks(assignee_id);

-- Create index on project_id to optimize queries filtering by project
CREATE INDEX idx_tasks_project_id ON task_management_service_schema.tasks(project_id);

-- Create index on status to improve query performance for filtering tasks by status
CREATE INDEX idx_tasks_status ON task_management_service_schema.tasks(status);

-- Create index on priority to optimize queries filtering tasks by priority
CREATE INDEX idx_tasks_priority ON task_management_service_schema.tasks(priority);

-- Create index on due_date to optimize queries based on task deadlines
CREATE INDEX idx_tasks_due_date ON task_management_service_schema.tasks(due_date);
