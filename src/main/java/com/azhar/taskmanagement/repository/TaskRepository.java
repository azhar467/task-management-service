package com.azhar.taskmanagement.repository;

import com.azhar.taskmanagement.dao.Task;
import com.azhar.taskmanagement.dao.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByProjectId(Long projectId);
    List<Task> findByAssigneeId(Long assigneeId);
    List<Task> findByStatus(TaskStatus status);
    
    @Query("SELECT t FROM Task t WHERE " +
           "(:projectId IS NULL OR t.project.id = :projectId) AND " +
           "(:assigneeId IS NULL OR t.assignee.id = :assigneeId) AND " +
           "(:status IS NULL OR t.status = :status)")
    List<Task> findByFilters(@Param("projectId") Long projectId, 
                            @Param("assigneeId") Long assigneeId, 
                            @Param("status") TaskStatus status);
}
