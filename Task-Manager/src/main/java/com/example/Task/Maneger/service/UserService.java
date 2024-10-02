package com.example.Task.Maneger.service;

import com.example.Task.Maneger.model.UserModel;
import com.example.Task.Maneger.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UserService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    EmailService emailService;
public String register(UserModel user , Model  model){
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

}
