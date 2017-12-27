package by.controllers;

import by.entity.Project;
import by.entity.Task;
import by.entity.User;
import by.entity.UserListForm;
import by.service.ProjectService;
import by.service.TaskService;
import by.service.UserFormValidation;
import by.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;

@Controller
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    HttpSession httpSession;
    @Autowired
    private UserFormValidation userFormValidation;

    //Главная страница
    @RequestMapping(value = "/projectPage", method = RequestMethod.GET)
    public String projectPage(Model model, SessionStatus sessionStatus) {
        model.addAttribute("project", new Project());
        model.addAttribute("user", new User());
        model.addAttribute("projectList", userService.getAllProjectsByUser(getPrincipal()));
        model.addAttribute("allExistedUsers", userService.getUsersList());
        return "projectPage";
    }
    //Добавляем Проект
    @RequestMapping(value = "/projectPage/add", method = RequestMethod.POST)
    public String addProject(@ModelAttribute("project") @Valid Project project,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            return "projectPage";
        }
        User user = userService.findByUsername(getPrincipal());
        project.getUserList().add(user);
        projectService.addProject(project);
        userService.updateUser(userService.findByUsername(getPrincipal()));
        return "redirect:/projectPage";
    }
    // Edit project =>>> add users
    @RequestMapping("/editProject/{id}")
    public String editTask(@PathVariable("id") int id, Model model) {
        Project currentProject = (Project)httpSession.getAttribute("currentProject");
        model.addAttribute("allExistedUsers", userService.getUsersList());
        model.addAttribute("projectList", userService.getAllProjectsByUser(getPrincipal()));
        return "projectPage";
    }
    //Переход на Детали проекта
    @RequestMapping(value = "/projectData/{id}", method = RequestMethod.GET)
    public String projectInfo(@PathVariable("id") int id, Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("user", new User());
        model.addAttribute("project", new Project());
        model.addAttribute("taskForm", new Task());
        model.addAttribute("userListForm", new UserListForm());
        httpSession.setAttribute("currentProject", projectService.getProject(id));
        model.addAttribute("taskList", taskService.getAllByProject_Id(id));
        model.addAttribute("userListForProject", projectService.getAllUserByProject(projectService.getProject(id)));
        model.addAttribute("allExistedUsers", userService.getUsersList());
        return "projectData";
    }

    // Редирект на Детали проекта
    @RequestMapping(value = "/projectData", method = RequestMethod.GET)
    public String projectData(Model model, HttpServletRequest request) {
        model.addAttribute("task", new Task());
        model.addAttribute("user", new User());
        model.addAttribute("project", new Project());
        model.addAttribute("taskForm", new Task());
        model.addAttribute("userListForm", new UserListForm());
        Project currentProject = (Project)httpSession.getAttribute("currentProject");
        model.addAttribute("userListForProject", projectService.getAllUserByProject(currentProject));
        model.addAttribute("allExistedUsers", userService.getUsersList());
        String check = request.getParameter("checkbox1");
        if (check!=null) {
            model.addAttribute("taskList", userService.getAllTasksByUser(getPrincipal(), currentProject));
        } else model.addAttribute("taskList", taskService.getAllByProject_Id(currentProject.getId()));
        return "projectData";
    }

    //Добавляем User в Project
    @RequestMapping(value = "/projectData/addUser", method = RequestMethod.POST)
    public String addUserToProject(@ModelAttribute("userListForm") @Valid UserListForm userListForm,
                             BindingResult result,
                             Model model) {
        model.addAttribute("task", new Task());
        userFormValidation.validate(userListForm, result);
        if (result.hasErrors()) {
            return "redirect:/projectData";
        }

        User user = userListForm.getUserForProject();
        Project currentProject = (Project)httpSession.getAttribute("currentProject");
        userService.addDeveloperToProject(currentProject, user);
        return "redirect:/projectData";
    }

    class ProjectEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text){
            User user = userService.findByUsername(text);
            setValue(user);
        }
    }

    @InitBinder("userListForm")
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(User.class, new ProjectEditor());
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}