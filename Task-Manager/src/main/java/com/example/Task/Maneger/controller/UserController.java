package com.example.Task.Maneger.controller;

import com.example.Task.Maneger.model.UserModel;
import com.example.Task.Maneger.repository.UsersRepository;
import com.example.Task.Maneger.service.EmailService;
import com.example.Task.Maneger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    EmailService emailService;
    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserModel());
        return "register";
    }

    @PostMapping("/register")
    public void register(UserModel user, Model model) {
        userService.register(user, model);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
