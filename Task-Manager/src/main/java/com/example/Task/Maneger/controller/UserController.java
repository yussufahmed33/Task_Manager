package com.example.Task.Maneger.controller;

import com.example.Task.Maneger.model.UserModel;
import com.example.Task.Maneger.repository.UsersRepository;
import com.example.Task.Maneger.service.EmailService;
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
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserModel());
        return "register";
    }
    @PostMapping("/register")
    public String register(UserModel user, Model model) {
        if (usersRepository.findByUsername(user.getUsername()) != null) {
            return "register";
        }else {
            model.addAttribute("user", new UserModel());
            String hashedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
            usersRepository.save(user);
            String email = user.getEmail();
            String subject = "Welcome! "+user.getUsername()+" Your Account Has Been Successfully Created";
            String text = "Dear " + user.getUsername() + ",\n\n" +
                    "Welcome to Task Management !\n\n" +
                    "We're excited to let you know that your account has been successfully created. You can now enjoy all the features and benefits of our service.\n\n" +
                    "Here are your account details:\n" +
                    "Username: " + user.getUsername() + "\n" +
                    "Email: " + user.getEmail() + "\n\n" +
                    "If you have any questions or need assistance, feel free to reach out to our support team.\n\n" +
                    "Thank you for joining us!\n\n" +
                    "Best regards,\n" +
                    "The Task Management Team";

            emailService.sendEmail(email,subject,text);
            return "redirect:/login";
        }
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
