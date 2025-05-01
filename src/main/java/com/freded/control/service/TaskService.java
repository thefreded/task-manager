package com.freded.control.service;

import com.freded.CustomWebApplicationException;
import com.freded.control.dto.TaskQueryDTO;
import com.freded.control.mappers.TaskMapper;
import com.freded.control.repository.TaskRepository;
import com.freded.control.dto.TaskDTO;
import com.freded.entity.TaskEntity;
import com.freded.control.dto.TaskSortAndPaginationDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;


/**
 * First task service implementation that persist task within application running cycle.
 * Moved to TaskDao now.
 */
@RequestScoped
public class TaskService {

    @Inject
    TaskRepository taskRepository;


    @Inject
    TaskMapper taskMapper;

    @Transactional
    public TaskDTO create(final TaskDTO task, final String currentUser) {
        TaskEntity newTask = taskMapper.toEntity(task);

        newTask.setCreatedBy(currentUser);
        return taskMapper.toDTOWithoutFiles(taskRepository.create(newTask));
    }

    public List<TaskDTO> getAll(final TaskSortAndPaginationDTO qParams, final String currentUser) {
        List<TaskEntity> taskEntities = taskRepository.readAll(currentUser, qParams);

        return taskMapper.toDTOList(taskEntities);
    }

    public TaskDTO get(final String taskId, final String currentUser, final TaskQueryDTO taskParams) {

        TaskEntity taskEntity = taskRepository.read(currentUser, taskId, taskParams);

        if (taskEntity == null) {
            return null;
        }


        if (taskParams.isLoadFiles()) {
            return taskMapper.toDTOWithFiles(taskEntity);
        } else {
            return taskMapper.toDTOWithoutFiles(taskEntity);
        }
    }


    @Transactional
    public String delete(final String taskId, final String currentUser) {

        TaskEntity task = taskRepository.read(currentUser, taskId);
        return taskRepository.delete(task);
    }

    @Transactional
    public TaskDTO update(final String taskId, final TaskDTO newTask, final String currentUser) {

        TaskEntity task = taskRepository.read(currentUser, taskId);

        if (task == null) {
            throw new CustomWebApplicationException("Task with Id:" + taskId, 204);
        }

        // update task with new task inputs
        task.setName(newTask.getName());
        task.setDescription(newTask.getDescription());

        // TODO: downside is I need to flush and refresh to get the latest DB Value. Disucss later.
        return taskMapper.toDTOWithoutFiles(taskRepository.update(task));
    }

}
