package by.service;

import by.entity.Project;
import by.entity.Task;
import by.entity.User;
import java.util.List;
import java.util.Set;

public interface UserService {
    User findByUsername(String username);
    User findByFirstNameAndLastNameAllIgnoreCase(String name, String surname);
    void addUser(User user);
    List<User> getUsersList();
    Set<Project> getAllProjectsByUser(String username);
    void addDeveloperToProject(Project project, User user);
    void updateUser(User user);
    Set<Task> getAllTasksByUser(String username, Project project);
}