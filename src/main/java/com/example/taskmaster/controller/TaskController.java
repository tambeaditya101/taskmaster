package com.example.taskmaster.controller;

import com.example.taskmaster.dto.task.CreateTaskRequest;
import com.example.taskmaster.dto.task.TaskResponse;
import com.example.taskmaster.dto.task.UpdateTaskRequest;
import com.example.taskmaster.entity.User;
import com.example.taskmaster.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody CreateTaskRequest request,
            @AuthenticationPrincipal User currentUser
    ) {

        TaskResponse response =
                taskService.createTask(request, currentUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/my")
    public ResponseEntity<List<TaskResponse>> getMyTasks(
            @AuthenticationPrincipal User currentUser
    ) {

        return ResponseEntity.ok(
                taskService.getMyTasks(currentUser)
        );
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable Long taskId,
            @AuthenticationPrincipal User currentUser
    ) {

        return ResponseEntity.ok(
                taskService.getTaskById(taskId, currentUser)
        );
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long taskId,
            @RequestBody UpdateTaskRequest request,
            @AuthenticationPrincipal User currentUser
    ) {

        return ResponseEntity.ok(
                taskService.updateTask(
                        taskId,
                        request,
                        currentUser
                )
        );
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long taskId,
            @AuthenticationPrincipal User currentUser
    ) {

        taskService.deleteTask(taskId, currentUser);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<TaskResponse> completeTask(
            @PathVariable Long taskId,
            @AuthenticationPrincipal User currentUser
    ) {

        return ResponseEntity.ok(
                taskService.completeTask(
                        taskId,
                        currentUser
                )
        );
    }
}