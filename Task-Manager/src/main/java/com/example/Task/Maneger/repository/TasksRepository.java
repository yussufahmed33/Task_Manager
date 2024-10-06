package com.example.Task.Maneger.repository;

import com.example.Task.Maneger.model.TaskModel;
import com.example.Task.Maneger.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TasksRepository extends JpaRepository<TaskModel, Long> {
    List<TaskModel> findByUserModel(UserModel userModel);

}
