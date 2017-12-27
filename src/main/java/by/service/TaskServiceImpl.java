package by.service;

import by.dao.TaskRepository;
import by.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    @Transactional
    public void addTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task getTask(int id) {
        return taskRepository.findOne(id);
    }

    @Override
    @Transactional
    public List<Task> getAllByProject_Id(int id) {
        return taskRepository.findAllByProject_Id(id);
    }

    @Override
    @Transactional
    public void updateTask(Task task) {
        taskRepository.save(task);
    }
}
