package com.freded.control.impl;

import com.freded.boundary.Task;
import com.freded.control.service.TaskService;
import com.freded.control.dto.TaskDTO;
import com.freded.control.dto.TaskSortAndPaginationDTO;
import jakarta.inject.Inject;

import java.util.List;

/**
 * * Implementation of the {@link Task} interface.
 */
public class TaskImpl implements Task {

    @Inject
    TaskService taskService;

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskDTO get(final String taskId) {
        return taskService.get(taskId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskDTO create(final TaskDTO task) {
        return taskService.create(task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TaskDTO> getAll(TaskSortAndPaginationDTO qParams) {
        return taskService.getAll(qParams);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String delete(final String taskId) {
        return taskService.delete(taskId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskDTO update(final String taskId, final TaskDTO task) {
        return taskService.update(taskId, task);
    }
}
