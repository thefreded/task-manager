package com.freded.control.service;

import com.freded.CustomWebApplicationException;
import com.freded.control.repository.TaskRepository;
import com.freded.control.dto.TaskDTO;
import com.freded.entity.TaskEntity;
import com.freded.control.dto.TaskSortAndPaginationDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * First task service implementation that persist task within application running cycle.
 * Moved to TaskDao now.
 */
@RequestScoped
public class TaskService {

    @Inject
    TaskRepository taskRepository;

    @Inject
    UserService userService;

    @Transactional
    public TaskDTO create(final TaskDTO task) {
        TaskEntity newTask = TaskEntity.fromDTO(task);

        String currentUser = userService.getUsername();
        newTask.setCreatedBy(currentUser);
        return taskRepository.create(newTask).toDTO();
    }

    public List<TaskDTO> getAll(TaskSortAndPaginationDTO qParams) {
        String currentUser = userService.getUsername();
        List<TaskEntity> taskEntities = taskRepository.readAll(currentUser, qParams);

        return taskEntities.stream()
                .map(TaskEntity::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO get(final String taskId) {
        String currentUser = userService.getUsername();
        return taskRepository.read(currentUser, taskId).toDTO();
    }

    @Transactional
    public String delete(final String taskId) {

        String currentUser = userService.getUsername();
        TaskEntity task = taskRepository.read(currentUser, taskId);
        return taskRepository.delete(task);
    }

    @Transactional
    public TaskDTO update(final String taskId, final TaskDTO newTask) {
        String currentUser = userService.getUsername();
        TaskEntity task = taskRepository.read(currentUser, taskId);

        if (task == null) {
            throw new CustomWebApplicationException("Task with Id:" + taskId, 204);
        }

        // update task with new task inputs
        task.setName(newTask.getName());
        task.setDescription(newTask.getDescription());
        task.setUpdatedAt(LocalDateTime.now());

        return taskRepository.update(task).toDTO();
    }

}
