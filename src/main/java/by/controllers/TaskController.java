package by.controllers;

import by.entity.Comment;
import by.entity.Project;
import by.entity.Task;
import by.entity.User;
import by.entity.UserListForm;
import by.service.CommentService;
import by.service.ProjectService;
import by.service.TaskService;
import by.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;

@Controller

public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    HttpSession httpSession;

    //Переход на Детали Task for comments and users
    @RequestMapping(value = "/taskData/{id}", method = RequestMethod.GET)
    public String projectInfo(@PathVariable("id") int id, Model model) {
        httpSession.setAttribute("currentTask", taskService.getTask(id));
        model.addAttribute("comment", new Comment());
        //model.addAttribute("user", new User());
        model.addAttribute("project", new Project());
        model.addAttribute("userListForm", new UserListForm());
        model.addAttribute("commentList", commentService.getAllByTask_Id(id));
        return "taskData";
    }

    //Добавляем Комментарий к Задаче
    @RequestMapping(value = "/commentData/addComment", method = RequestMethod.POST)
    public String taskPage(@ModelAttribute("comment") Comment comment,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            return "taskData";
        }
        Task currentTask = (Task)httpSession.getAttribute("currentTask");
        model.addAttribute("currentTask", currentTask);
        comment.setTask(currentTask);
        commentService.addComment(comment);
        return "redirect:/taskData";
    }

    // Редирект на Детали Task
    @RequestMapping(value = "/taskData", method = RequestMethod.GET)
    public String projectData(Model model) {
        Task currentTask = (Task)httpSession.getAttribute("currentTask");
        //model.addAttribute("user", new User());
        model.addAttribute("project", new Project());
        model.addAttribute("comment", new Comment());
        model.addAttribute("userListForm", new UserListForm());
        model.addAttribute("commentList", commentService.getAllByTask_Id(currentTask.getId()));
        return "taskData";
    }

    //Удаляем Comment
    @RequestMapping("/delete/{id}")
    public String deleteSong(@PathVariable("id") int id) {
        commentService.deleteComment(id);
        return "redirect:/taskData";
    }

    //Редактируем Comment
    @RequestMapping("/edit/{id}")
    public String editSong(@PathVariable("id") int id, Model model) {
        model.addAttribute("userListForm", new UserListForm());
        //model.addAttribute("user", new User());
        model.addAttribute("project", new Project());
        Task currentTask = (Task)httpSession.getAttribute("currentTask");
        model.addAttribute("comment", commentService.getComment(id));
        model.addAttribute("commentList", commentService.getAllByTask_Id(currentTask.getId()));
        return "taskData";
    }

    //Переход на Детали Comment
    @RequestMapping(value = "/commentData/{id}", method = RequestMethod.GET)
    public String commentInfo(@PathVariable("id") int id, Model model) {
        model.addAttribute("currentComment", commentService.getComment(id));
        return "commentData";
    }

    //Добавляем задачу в Детали Проекта
    @RequestMapping(value = "/projectData/addTask", method = RequestMethod.POST)
    public String addSong(@ModelAttribute("task") @Valid Task task, BindingResult result, Model model) {
        model.addAttribute("userListForm", new UserListForm());
        Project currentProject = (Project)httpSession.getAttribute("currentProject");
        if (result.hasErrors()) {
            return "projectData";
        }
        //Проверка возможности редактирования полей Task - плохая реализация -> переделать
        String role = "ROLE_USER";
        boolean isAuth1 = SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(role));
        boolean isAuth2 = task.getUser().getUsername().equals(getPrincipal());
        if (task.getId()==0) {
            task.setProject(currentProject);
            this.taskService.addTask(task);
        } else {
            if (isAuth1 && !isAuth2) {
                return "redirect:/projectData";
            } else {
                task.setProject(currentProject);
                this.taskService.updateTask(task);
            }
        }
        return "redirect:/projectData";
    }

    //Добавляем User в Task
    @RequestMapping("/editTask/{id}")
    public String editTask(@PathVariable("id") int id, Model model) {
        model.addAttribute("userListForm", new UserListForm());
        model.addAttribute("task", taskService.getTask(id));
        Project currentProject = (Project)httpSession.getAttribute("currentProject");
        model.addAttribute("userListForProject", projectService.getAllUserByProject(currentProject));
        model.addAttribute("allExistedUsers", userService.getUsersList());
        model.addAttribute("taskList", taskService.getAllByProject_Id(currentProject.getId()));
        return "projectData";
    }
    // Фильтр Task по пользователю - Не работает, checkbox всегда = null ???
    @RequestMapping(value = "/taskListByUser")
    public String showMyTasks() {
        return "redirect:/projectData";
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

    class TaskEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text){
            User user = userService.findByUsername(text);
            setValue(user);
        }
    }

    @InitBinder("task")
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(User.class, new TaskEditor());
    }
}