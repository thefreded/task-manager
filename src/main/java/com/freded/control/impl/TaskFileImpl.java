package com.freded.control.impl;

import com.freded.boundary.TaskFile;
import com.freded.control.service.TaskFileService;
import com.freded.control.dto.TaskFileDTO;
import com.freded.control.dto.TaskFileSortAndPaginationDTO;
import com.freded.control.dto.TaskFileUploadDTO;
import jakarta.inject.Inject;

import java.util.List;

public class TaskFileImpl implements TaskFile {
    @Inject
    TaskFileService taskFileService;

    @Override
    public TaskFileDTO save(final String taskId, final TaskFileUploadDTO form) {
        return taskFileService.save(taskId, form);
    }

    @Override
    public TaskFileDTO get(String taskFileId) {
        return taskFileService.get(taskFileId);
    }

    @Override
    public List<TaskFileDTO> getAll(final String taskId, final TaskFileSortAndPaginationDTO qParams) {
        return taskFileService.getAll(taskId, qParams);
    }
}
