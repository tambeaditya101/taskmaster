package com.example.taskmaster.service;

import com.example.taskmaster.dto.task.CreateTaskRequest;
import com.example.taskmaster.dto.task.TaskResponse;
import com.example.taskmaster.entity.Task;
import com.example.taskmaster.entity.TaskStatus;
import com.example.taskmaster.entity.User;
import com.example.taskmaster.exception.DuplicateResourceException;
import com.example.taskmaster.repository.TaskRepository;
import com.example.taskmaster.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.taskmaster.dto.task.UpdateTaskRequest;
import com.example.taskmaster.exception.ResourceNotFoundException;

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

    @Transactional(readOnly = true)
    public TaskResponse getTaskById(
            Long taskId,
            User currentUser
    ) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with id: " + taskId
                        )
                );

        return new TaskResponse(task);
    }

    @Transactional
    public TaskResponse updateTask(
            Long taskId,
            UpdateTaskRequest request,
            User currentUser
    ) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with id: " + taskId
                        )
                );

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }

        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }

        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        if (request.getAssignedToId() != null) {

            User assignedUser = userRepository
                    .findById(request.getAssignedToId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "User not found with id: "
                                            + request.getAssignedToId()
                            )
                    );

            task.setAssignedTo(assignedUser);
        }

        return new TaskResponse(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(
            Long taskId,
            User currentUser
    ) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with id: " + taskId
                        )
                );

        taskRepository.delete(task);
    }

    @Transactional
    public TaskResponse completeTask(
            Long taskId,
            User currentUser
    ) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with id: " + taskId
                        )
                );

        task.setStatus(TaskStatus.COMPLETED);

        return new TaskResponse(taskRepository.save(task));
    }
}