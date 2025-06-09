// src/main/java/com/dio/taskmanager/controller/TaskController.java
package com.dio.taskmanager.controller;

import com.dio.taskmanager.model.Task;
import com.dio.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/tasks") // Define o caminho base para todos os endpoints neste controlador
@Tag(name = "Task Management", description = "API para gerenciamento de tarefas") // Tag para o Swagger
public class TaskController {

    private final TaskService taskService; // Injeção de dependência do TaskService

    // Construtor para injeção de dependência do TaskService
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Cria uma nova tarefa", description = "Adiciona uma nova tarefa ao sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    })
    @PostMapping // Mapeia requisições POST para /tasks
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask); // Retorna 201 Created
    }

    @Operation(summary = "Lista todas as tarefas", description = "Retorna uma lista de todas as tarefas cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class)))
    })
    @GetMapping // Mapeia requisições GET para /tasks
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.findAllTasks();
        return ResponseEntity.ok(tasks); // Retorna 200 OK
    }

    @Operation(summary = "Busca uma tarefa por ID", description = "Retorna uma tarefa específica com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada", content = @Content)
    })
    @GetMapping("/{id}") // Mapeia requisições GET para /tasks/{id}
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.findTaskById(id);
        return ResponseEntity.ok(task); // Retorna 200 OK
    }

    @Operation(summary = "Atualiza uma tarefa existente", description = "Atualiza os detalhes de uma tarefa com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    })
    @PutMapping("/{id}") // Mapeia requisições PUT para /tasks/{id}
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Task updatedTask = taskService.updateTask(id, taskDetails);
        return ResponseEntity.ok(updatedTask); // Retorna 200 OK
    }

    @Operation(summary = "Deleta uma tarefa", description = "Remove uma tarefa do sistema com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada", content = @Content)
    })
    @DeleteMapping("/{id}") // Mapeia requisições DELETE para /tasks/{id}
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    @Operation(summary = "Marca/desmarca uma tarefa como concluída", description = "Atualiza o status 'completed' de uma tarefa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status da tarefa atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada", content = @Content)
    })
    @PatchMapping("/{id}/complete") // Mapeia requisições PATCH para /tasks/{id}/complete
    public ResponseEntity<Task> completeTask(@PathVariable Long id, @RequestParam boolean completed) {
        Task updatedTask = taskService.completeTask(id, completed);
        return ResponseEntity.ok(updatedTask); // Retorna 200 OK
    }
}