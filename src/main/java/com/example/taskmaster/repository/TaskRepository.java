package com.example.taskmaster.repository;

import com.example.taskmaster.entity.Task;
import com.example.taskmaster.entity.TaskStatus;
import com.example.taskmaster.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedTo(User user);

    List<Task> findByAssignedToAndStatus(
            User user,
            TaskStatus status
    );

    List<Task> findByAssignedToAndTitleContainingIgnoreCaseOrAssignedToAndDescriptionContainingIgnoreCase(
            User user1,
            String title,
            User user2,
            String description
    );
}