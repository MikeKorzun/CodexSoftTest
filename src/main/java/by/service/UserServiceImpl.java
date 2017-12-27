package by.service;

import by.dao.UserRepository;
import by.entity.Project;
import by.entity.Task;
import by.entity.User;
import javafx.collections.transformation.SortedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    /*@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;*/

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public User findByFirstNameAndLastNameAllIgnoreCase(String name, String surname) {
        return userRepository.findByFirstNameAndLastNameAllIgnoreCase(name, surname);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public List<User> getUsersList() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional
    public Set<Project> getAllProjectsByUser(String username) {
        User user = findByUsername(username);
        return user.getProjectList();
    }

    @Override
    @Transactional
    public Set<Task> getAllTasksByUser(String username, Project project) {
        User user = findByUsername(username);
        Set<Task> allTasksByProjectByUser = new LinkedHashSet<>(user.getTaskList());
        for (Task t: allTasksByProjectByUser) {
            if (t.getProject().getId()!=project.getId()) {
                allTasksByProjectByUser.remove(t);
            }
        }
        return allTasksByProjectByUser;
    }

    @Override
    @Transactional
    public void addDeveloperToProject(Project project, User user) {
        if (!project.getUserList().contains(user)) {
            project.getUserList().add(user);
            user.getProjectList().add(project);
        }
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }
}