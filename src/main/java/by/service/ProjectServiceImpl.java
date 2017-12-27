package by.service;

import by.dao.ProjectRepository;
import by.entity.Project;
import by.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

@Component
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    ProjectRepository projectRepository;

    @Transactional
    public void addProject(Project project) {
        projectRepository.save(project);
    }
    @Transactional
    public Project getProject(int id) {
        return projectRepository.findOne(id);
    }
    @Transactional
    public List<Project> getAllProjects() {
        return (List<Project>) projectRepository.findAll();
    }
    @Transactional
    public Set<User> getAllUserByProject(Project project) {
        return project.getUserList();
    }
}