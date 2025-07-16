-- Add audit columns to existing tables
ALTER TABLE task_management_service_schema.users 
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);

ALTER TABLE task_management_service_schema.projects 
ADD COLUMN created_by_audit VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);

ALTER TABLE task_management_service_schema.tasks 
ADD COLUMN created_by_audit VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);