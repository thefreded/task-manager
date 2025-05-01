package com.freded.control.impl;

import com.freded.boundary.TaskFile;
import com.freded.control.dto.TaskFileDTO;
import com.freded.control.dto.TaskFileSortAndPaginationDTO;
import com.freded.control.dto.TaskFileUploadDTO;
import com.freded.control.service.TaskFileService;
import com.freded.control.service.TaskFileUploadService;
import com.freded.control.service.UserService;
import jakarta.inject.Inject;

import java.util.List;

public class TaskFileImpl implements TaskFile {
    @Inject
    TaskFileService taskFileService;

    @Inject
    UserService userService;

    @Inject
    TaskFileUploadService taskFileUploadService;

    @Override
    public TaskFileDTO save(final String taskId, final TaskFileUploadDTO form) {
        String currentUser = userService.getUsername();
        return taskFileUploadService.saveFile(taskId, form, currentUser);
    }

    @Override
    public TaskFileDTO get(String taskFileId) {

        String currentUser = userService.getUsername();
        return taskFileService.get(taskFileId, currentUser);
    }

    @Override
    public List<TaskFileDTO> getAll(final String taskId, final TaskFileSortAndPaginationDTO qParams) {
        String currentUser = userService.getUsername();
        return taskFileService.getAll(taskId, qParams, currentUser);
    }
}
