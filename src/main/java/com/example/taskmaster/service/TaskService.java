package com.example.taskmaster.service;

import com.example.taskmaster.dto.task.CreateTaskRequest;
import com.example.taskmaster.dto.task.TaskResponse;
import com.example.taskmaster.entity.Task;
import com.example.taskmaster.entity.User;
import com.example.taskmaster.exception.DuplicateResourceException;
import com.example.taskmaster.repository.TaskRepository;
import com.example.taskmaster.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(
            TaskRepository taskRepository,
            UserRepository userRepository
    ) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskResponse createTask(
            CreateTaskRequest request,
            User currentUser
    ) {

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setCreatedBy(currentUser);

        if (request.getAssignedToId() != null) {

            User assignedUser = userRepository
                    .findById(request.getAssignedToId())
                    .orElseThrow(() ->
                            new RuntimeException("Assigned user not found"));

            task.setAssignedTo(assignedUser);
        }

        Task savedTask = taskRepository.save(task);

        return new TaskResponse(savedTask);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getMyTasks(User currentUser){

        return taskRepository
                .findByAssignedTo(currentUser)
                .stream()
                .map(TaskResponse::new)
                .toList();
    }
}