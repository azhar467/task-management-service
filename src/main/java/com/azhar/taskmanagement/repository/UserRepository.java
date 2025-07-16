package com.azhar.taskmanagement.repository;

import com.azhar.taskmanagement.dao.User;
import com.azhar.taskmanagement.dao.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByName(String createdByName);
    List<User> findByRole(UserRole role);
}
