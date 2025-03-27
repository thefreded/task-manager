package com.freded.control.service;

import com.freded.CustomWebApplicationException;
import com.freded.control.repository.TaskFileRepository;
import com.freded.control.dto.TaskFileDTO;
import com.freded.entity.TaskFileEntity;
import com.freded.control.dto.TaskFileSortAndPaginationDTO;
import com.freded.control.dto.TaskFileUploadDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class TaskFileService {

    @Inject
    TaskFileRepository taskFileRepository;

    @Inject
    TaskService taskService;

    @Inject
    UserService userService;

    @Transactional
    public TaskFileDTO save(final String taskId, final TaskFileUploadDTO form) {
        String fileName = form.getFileName();
        String fileType = form.getFileType();
        byte[] fileData = form.getFileData();

        // if no file, then it is a bad request
        if (fileData == null || fileData.length == 0) {
            throw new CustomWebApplicationException("File is missing", 400);
        }

        // if no fileName, then it is a bad request
        if (fileName.isBlank()) {
            throw new CustomWebApplicationException("File name is missing", 400);
        }


        // if no task with the id assigned to the user, then it is a bad request
        if (taskService.get(taskId) == null) {
            throw new CustomWebApplicationException("Wrong taskId", 400);
        }

        TaskFileEntity taskFileEntity = new TaskFileEntity();
        taskFileEntity.setFileName(fileName);
        taskFileEntity.setFileType(fileType);
        taskFileEntity.setFileData(fileData);
        taskFileEntity.setTaskId(taskId);
        taskFileEntity.setUploadedBy(userService.getUsername());

        return taskFileRepository.save(taskFileEntity).toDTO();
    }


    public TaskFileDTO get(String taskFileId) {
        String currentUser = userService.getUsername();
        return taskFileRepository.read(currentUser, taskFileId).toDTO();
    }


    public List<TaskFileDTO> getAll(final String taskId, final TaskFileSortAndPaginationDTO qParams) {
        String currentUser = userService.getUsername();
        List<TaskFileEntity> taskFileEntities = taskFileRepository.readAll(currentUser, taskId, qParams);

        return taskFileEntities.stream().map(TaskFileEntity::toDTO).collect(Collectors.toList());

    }
}
