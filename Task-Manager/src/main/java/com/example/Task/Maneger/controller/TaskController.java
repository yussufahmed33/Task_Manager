package com.example.Task.Maneger.controller;

import com.example.Task.Maneger.model.TaskModel;
import com.example.Task.Maneger.repository.TasksRepository;
import com.example.Task.Maneger.repository.UsersRepository;
import com.example.Task.Maneger.service.EmailService;
import com.example.Task.Maneger.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;


@Controller
public class TaskController {
    private static final String TASKS_REDIRECT="redirect:/tasks";
    @Autowired
    TasksRepository tasksRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    TaskService taskService;

    @GetMapping("/tasks")
    public String home(Model model, Principal principal) {
       taskService.showTasks(principal, model);
        return "tasks";
    }

    @PostMapping("/tasks/create")
    public String createTask(@ModelAttribute TaskModel task, Principal principal) {
       taskService.createTask(task, principal);
       return TASKS_REDIRECT;
    }

    @GetMapping("/tasks/create")
    public String getCreatTask(Model model) {
        model.addAttribute("taskModel", new TaskModel());
        return "createtask";
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable long id) {
        tasksRepository.deleteById(id);
        return TASKS_REDIRECT;
    }

    @PostMapping("/tasks/edit/{id}")
    public String editTask(@PathVariable long id, @ModelAttribute("task") TaskModel updatedTaskModel) {
       taskService.edittask(id,updatedTaskModel);

       return TASKS_REDIRECT;
    }

        @GetMapping("/tasks/edit/{id}")

        public String getEditTask ( @PathVariable long id, Model model, Principal principal){
            taskService.getedittask(id, model, principal);
            return "edittask";
    }




}

