package com.freded.control.service;

import com.freded.control.dto.TaskFileDTO;
import com.freded.control.dto.TaskFileSortAndPaginationDTO;
import com.freded.control.mappers.TaskFileMapper;
import com.freded.control.repository.TaskFileRepository;
import com.freded.entity.TaskFileEntity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;


@RequestScoped
public class TaskFileService {

    @Inject
    TaskFileRepository taskFileRepository;


    @Inject
    TaskFileMapper taskFileMapper;


    public TaskFileDTO get(final String taskFileId, final String currentUser) {
        return taskFileMapper.toDTO(taskFileRepository.read(currentUser, taskFileId));
    }


    public List<TaskFileDTO> getAll(final String taskId, final TaskFileSortAndPaginationDTO qParams, final String currentUser) {

        List<TaskFileEntity> taskFileEntities = taskFileRepository.readAll(currentUser, taskId, qParams);

        return taskFileMapper.toDTOList(taskFileEntities);

    }
}
