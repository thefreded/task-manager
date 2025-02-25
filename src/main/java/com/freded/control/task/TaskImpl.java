package com.freded.control.task;

import com.freded.CustomLog;
import com.freded.boundary.Task;
import com.freded.entity.PaginationAndSortingDTO;
import com.freded.entity.TaskDTO;
import com.freded.entity.TaskSortAndPaginationDTO;
import jakarta.inject.Inject;

import java.util.List;

/**
 * * Implementation of the {@link Task} interface.
 */
public class TaskImpl implements Task {

    @Inject
    TaskDao taskDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskDTO getTask(final String taskId) {
        return taskDao.getTask(taskId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskDTO createTask(final TaskDTO task) {
        return taskDao.addTask(task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CustomLog
    public List<TaskDTO> getAllTask(
            TaskSortAndPaginationDTO qParams
    ) {
        return taskDao.getAllTask(qParams);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteTask(final String taskId) {
        return taskDao.deleteTask(taskId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskDTO updateTask(final String taskId, final TaskDTO task) {
        return taskDao.updateTask(taskId, task);
    }
}
