package com.example.Task.Maneger.service;

import com.example.Task.Maneger.config.UserDetailesService;
import com.example.Task.Maneger.model.TaskModel;
import com.example.Task.Maneger.model.UserModel;
import com.example.Task.Maneger.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmailService {
    @Autowired
    private TasksRepository tasksRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Async
public void sendEmail(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    mailSender.send(message);
}
@Scheduled(cron = "0 32 12 * * ?")
    public void checkTasksForDueDate() {
    List<TaskModel> tasks = tasksRepository.findAll();
    LocalDate today = LocalDate.now();
    for (TaskModel task : tasks) {
        if( task.getExpiredDate().isEqual(today)) {
            UserModel user = task.getUserModel();
            String email = user.getEmail();
            String subject = "Reminder: Task '" + task.getTitle() + "' is Due Today";
            String text = "Hello " + user.getUsername() + ",\n\n"
                    + "This is a reminder that the following task is due today:\n\n"
                    + "Task: " + task.getTitle() + "\n"
                    + "Description: " + task.getDescription() + "\n\n"
                    + "Please ensure that it is completed on time.\n\n"
                    + "Best regards,\n"
                    + "Your Task Management Team";

            sendEmail(email, subject, text);
        }
    }
}
}
