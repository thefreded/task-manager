package com.freded.control.impl;

import com.freded.boundary.Task;
import com.freded.control.dto.TaskQueryDTO;
import com.freded.control.service.TaskService;
import com.freded.control.dto.TaskDTO;
import com.freded.control.dto.TaskSortAndPaginationDTO;
import com.freded.control.service.UserService;
import jakarta.inject.Inject;

import java.util.List;

/**
 * * Implementation of the {@link Task} interface.
 */
public class TaskImpl implements Task {

    @Inject
    TaskService taskService;

    @Inject
    UserService userService;

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskDTO get(final String taskId, final TaskQueryDTO taskParams) {
        String currentUser = userService.getUsername();
        return taskService.get(taskId, currentUser, taskParams);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskDTO create(final TaskDTO task) {
        String currentUser =  userService.getUsername();
        return taskService.create(task, currentUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TaskDTO> getAll(final TaskSortAndPaginationDTO qParams) {
        String currentUser =  userService.getUsername();
        return taskService.getAll(qParams, currentUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String delete(final String taskId) {
        String currentUser =  userService.getUsername();
        return taskService.delete(taskId, currentUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskDTO update(final String taskId, final TaskDTO task) {
        String currentUser =  userService.getUsername();
        return taskService.update(taskId, task, currentUser);
    }
}
