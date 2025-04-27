package com.freded.entity;

import com.freded.control.dto.TaskFileDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskFileEntity {

    /**
     * Unique identifier for the file.
     */
    @Id
    private String id = UUID.randomUUID().toString();

    @NotNull(message = "File name cannot be null")
    @Column(unique = true, nullable = false)
    private String fileName;

    private String fileType;

    private String taskId;

    private LocalDateTime uploadedAt = LocalDateTime.now();

    private String uploadedBy;

    @Lob
    @Column(nullable = false)
    private byte[] fileData;


    public static TaskFileEntity fromDTO(TaskFileDTO taskFileDTO) {
        TaskFileEntity taskFileEntity = new TaskFileEntity();
        taskFileEntity.setFileName(taskFileDTO.getFileName());
        taskFileEntity.setFileType(taskFileDTO.getFileType());
        taskFileEntity.setTaskId(taskFileDTO.getTaskId());
        taskFileEntity.setFileData(taskFileEntity.getFileData());

        return taskFileEntity;
    }

    public TaskFileDTO toDTO() {
        TaskFileDTO taskFileDTO = new TaskFileDTO();
        taskFileDTO.setId(this.id);
        taskFileDTO.setFileName(this.fileName);
        taskFileDTO.setFileType(this.fileType);
        taskFileDTO.setTaskId(this.taskId);
        taskFileDTO.setFileData(this.fileData);
        return taskFileDTO;
    }
}
