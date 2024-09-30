package com.example.Task.Maneger.repository;

import com.example.Task.Maneger.model.TaskModel;
import com.example.Task.Maneger.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TasksRepository extends JpaRepository<TaskModel, Long> {
    Optional<TaskModel> findTaskModelByUserModelId(Long userModelId);
    List<TaskModel> findByUserModel(UserModel userModel);

}
