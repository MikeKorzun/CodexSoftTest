package by.service;


import by.entity.Task;
import java.util.List;

public interface TaskService {
    void addTask(Task task);
    Task getTask(int id);
    List<Task> getAllByProject_Id(int id);
    void updateTask(Task task);
}
