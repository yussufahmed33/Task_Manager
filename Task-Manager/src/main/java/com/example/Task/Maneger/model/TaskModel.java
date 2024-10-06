package com.example.Task.Maneger.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "tasks")
public class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDate expiredDate;
    private String status;
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel userModel;
}
