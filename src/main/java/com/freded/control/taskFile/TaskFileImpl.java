package com.freded.control.taskFile;

import com.freded.boundary.TaskFile;
import com.freded.entity.TaskFileDTO;
import com.freded.entity.TaskFileSortAndPaginationDTO;
import com.freded.entity.TaskFileUploadDTO;
import com.freded.entity.TaskSortAndPaginationDTO;
import jakarta.inject.Inject;

import java.util.List;

public class  TaskFileImpl implements TaskFile {
    @Inject
    TaskFileDao taskFileDao;

    @Override
    public TaskFileDTO saveTaskFile(final String taskId, final TaskFileUploadDTO form) {
        return taskFileDao.saveTaskFile(taskId, form);
    }

    @Override
    public TaskFileDTO getTaskFile(String taskFileId) {
        return taskFileDao.getTaskFile(taskFileId);
    }

    @Override
    public List<TaskFileDTO> getAllTaskFiles(final String taskId, final TaskFileSortAndPaginationDTO qParams) {
        return taskFileDao.getAllTaskFiles(taskId, qParams);
    }
}
