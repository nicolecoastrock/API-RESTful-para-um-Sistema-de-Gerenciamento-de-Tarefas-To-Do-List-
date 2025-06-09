// src/main/java/com/dio/taskmanager/model/Task.java
package com.dio.taskmanager.model;

import jakarta.persistence.*; 
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate; // Para a data de vencimento

@Entity 
@Table(name = "tasks") // Define o nome da tabela no banco de dados
@Data // Anotação do Lombok para gerar getters, setters, toString, equals e hashCode
@NoArgsConstructor 
@AllArgsConstructor 
public class Task {

    @Id // Marca o campo 'id' como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura a geração automática do ID (auto-incremento)
    private Long id;

    @Column(nullable = false) 
    private String title;

    @Column(columnDefinition = "TEXT") // Define o tipo da coluna para texto longo
    private String description;

    private LocalDate dueDate; // Data de vencimento da tarefa

    private boolean completed = false; // Estado da tarefa: true se concluída, false caso contrário (padrão false)

    // Construtor adicional para facilitar a criação de tarefas sem ID (que será gerado automaticamente)
    public Task(String title, String description, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = false; // Garante que novas tarefas começam como não concluídas
    }
}