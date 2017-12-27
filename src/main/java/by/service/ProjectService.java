package by.service;

import by.entity.Project;
import by.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.Set;

public interface ProjectService {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void addProject(Project project);
    Project getProject(int id);
    Set<User> getAllUserByProject(Project project);
}
