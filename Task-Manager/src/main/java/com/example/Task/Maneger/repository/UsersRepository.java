package com.example.Task.Maneger.repository;

import com.example.Task.Maneger.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
}
