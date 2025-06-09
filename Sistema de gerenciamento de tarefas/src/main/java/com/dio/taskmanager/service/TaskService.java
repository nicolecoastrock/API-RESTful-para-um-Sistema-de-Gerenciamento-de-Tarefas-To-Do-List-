// src/main/java/com/dio/taskmanager/service/TaskService.java
package com.dio.taskmanager.service;

import com.dio.taskmanager.model.Task;
import com.dio.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service // Indica que esta classe é um componente de serviço Spring
public class TaskService {

    private final TaskRepository taskRepository; // Injeção de dependência do TaskRepository

    // Construtor para injeção de dependência do TaskRepository
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Método para criar uma nova tarefa
    public Task createTask(Task task) {
        // Você pode adicionar lógicas de validação aqui, por exemplo:
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O título da tarefa não pode ser vazio.");
        }
        return taskRepository.save(task);
    }

    // Método para listar todas as tarefas
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    // Método para buscar uma tarefa pelo ID
    public Task findTaskById(Long id) {
        // O .orElseThrow() lança uma exceção se a tarefa não for encontrada
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada com o ID: " + id));
    }

    // Método para atualizar uma tarefa existente
    public Task updateTask(Long id, Task taskDetails) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada com o ID: " + id));

        // Atualiza os campos da tarefa existente com os detalhes fornecidos
        existingTask.setTitle(taskDetails.getTitle());
        existingTask.setDescription(taskDetails.getDescription());
        existingTask.setDueDate(taskDetails.getDueDate());
        existingTask.setCompleted(taskDetails.isCompleted()); // Permite atualizar o status de conclusão

        return taskRepository.save(existingTask); // Salva a tarefa atualizada
    }

    // Método para deletar uma tarefa
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada com o ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    // Método específico para marcar/desmarcar uma tarefa como concluída
    public Task completeTask(Long id, boolean completed) {
        Task taskToUpdate = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada com o ID: " + id));

        taskToUpdate.setCompleted(completed);
        return taskRepository.save(taskToUpdate);
    }
}