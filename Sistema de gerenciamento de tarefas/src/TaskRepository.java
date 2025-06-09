// src/main/java/com/dio/taskmanager/repository/TaskRepository.java
package com.dio.taskmanager.repository;

import com.dio.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface TaskRepository extends JpaRepository<Task, Long> {
    