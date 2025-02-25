package com.freded.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
public class TaskFileDTO {

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



    @Override
    public String toString(){
        ObjectMapper mapper =  new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
