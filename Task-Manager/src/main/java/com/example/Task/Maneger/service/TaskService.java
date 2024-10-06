package com.example.Task.Maneger.service;

import com.example.Task.Maneger.model.TaskModel;
import com.example.Task.Maneger.model.UserModel;
import com.example.Task.Maneger.repository.TasksRepository;
import com.example.Task.Maneger.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {
    private static final String tasksRedirect="redirect:/tasks";
    @Autowired
    TasksRepository tasksRepository;
    @Autowired
    UserService userService;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    EmailService emailService;
    public void showTasks(Principal principal, Model model){
        UserModel userModel1 = usersRepository.findByUsername(principal.getName());
        List<TaskModel> userTasks = tasksRepository.findByUserModel(userModel1);
        model.addAttribute("userTasks", userTasks);
    }
public String getedittask( long id, Model model, Principal principal){
    try {
        TaskModel taskModel = tasksRepository.findById(id).get(); //.orElseThrow(() -> new IllegalArgumentException("Invalid task ID"));

        if (principal.getName().equals(tasksRepository.findById(id).get().getUserModel().getUsername())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = taskModel.getExpiredDate().format(formatter);
            model.addAttribute("task", taskModel);
            model.addAttribute("formattedExpiredDate", formattedDate);

            return "edittask";
        } else {
            return tasksRedirect;

        }
    }catch (NoSuchElementException e){
        return tasksRedirect;
    }
}
public String edittask( long id, Model model, TaskModel taskModel, TaskModel updatedTaskModel, Principal principal){

    TaskModel existingTaskModel = tasksRepository.findById(id).get();
    existingTaskModel.setTitle(updatedTaskModel.getTitle());
    existingTaskModel.setDescription(updatedTaskModel.getDescription());
    existingTaskModel.setExpiredDate(updatedTaskModel.getExpiredDate());
    existingTaskModel.setStatus(updatedTaskModel.getStatus());
    tasksRepository.save(existingTaskModel);
    return tasksRedirect;
}


    public String createTask(TaskModel task,Principal principal) {
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
        return tasksRedirect;
    }
}
