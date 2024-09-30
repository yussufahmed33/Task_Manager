package com.example.Task.Maneger.controller;

import com.example.Task.Maneger.model.TaskModel;
import com.example.Task.Maneger.model.UserModel;
import com.example.Task.Maneger.repository.TasksRepository;
import com.example.Task.Maneger.repository.UsersRepository;
import com.example.Task.Maneger.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {
    @Autowired
    TasksRepository tasksRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    EmailService emailService;

    @GetMapping("/tasks")
    public String home(UserModel userModel, Model model, Principal principal) {
        UserModel userModel1 = usersRepository.findByUsername(principal.getName());
        List<TaskModel> userTasks = tasksRepository.findByUserModel(userModel1);
        model.addAttribute("userTasks", userTasks);
        return "tasks";
    }

    @PostMapping("/tasks/create")
    public String createTask(@ModelAttribute TaskModel task, Principal principal) {
        String username = principal.getName();
        UserModel user = usersRepository.findByUsername(username);

        if (user == null) {
            throw new IllegalStateException("User not found.");
        }
        task.setUserModel(user);

        tasksRepository.save(task);
        String email = user.getEmail();
        String subject = "Task Creation Successful!";
        String text = "Dear User,\n\nYour task \"" + task.getTitle() + "\" has been successfully created!"
                + "\n\nYou can now view and manage your tasks in the Task Manager."
                // +"\n\n your task link is: https://localhost:9080/tasks/" + task.getId() + "/"
                + "\n\nBest regards,\nTask Manager Team";
        emailService.sendEmail(email, subject, text);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/create")
    public String getCreatTask(Model model) {
        model.addAttribute("taskModel", new TaskModel());
        return "createtask";
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable long id) {
        tasksRepository.deleteById(id);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/edit/{id}")
    public String editTask(@PathVariable long id, Model model, TaskModel taskModel, @ModelAttribute("task") TaskModel updatedTaskModel, Principal principal) {

        TaskModel existingTaskModel = tasksRepository.findById(id).get();
        existingTaskModel.setTitle(updatedTaskModel.getTitle());
        existingTaskModel.setDescription(updatedTaskModel.getDescription());
        existingTaskModel.setExpiredDate(updatedTaskModel.getExpiredDate());
        existingTaskModel.setStatus(updatedTaskModel.getStatus());
        tasksRepository.save(existingTaskModel);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/edit/{id}")
    public String getEditTask(@PathVariable long id, Model model, Principal principal) {

        TaskModel taskModel = tasksRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task ID"));

        if (principal.getName().equals(tasksRepository.findById(id).get().getUserModel().getUsername()) && tasksRepository.findById(id) != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = taskModel.getExpiredDate().format(formatter);
            model.addAttribute("task", taskModel);
            model.addAttribute("formattedExpiredDate", formattedDate);

            return "edittask";
        } else {
            return "redirect:/tasks";

        }
        }


}

