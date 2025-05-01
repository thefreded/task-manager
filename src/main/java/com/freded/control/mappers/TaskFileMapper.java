package com.freded.control.mappers;

import com.freded.control.dto.TaskFileDTO;
import com.freded.entity.TaskFileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper()
public interface TaskFileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uploadedAt", ignore = true)
    @Mapping(target = "uploadedBy", ignore = true)
    @Mapping(target = "task", ignore = true)
    TaskFileEntity toEntity(TaskFileDTO taskFileDTO);

    // @Mapping(target = "task", ignore = true)
    TaskFileDTO toDTO(TaskFileEntity taskFileEntity);

    List<TaskFileDTO> toDTOList(List<TaskFileEntity> taskFileEntityList);

    List<TaskFileEntity> toEntityList(List<TaskFileDTO> taskFileDTOList);
}
