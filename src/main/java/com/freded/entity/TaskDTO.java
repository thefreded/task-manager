package com.freded.entity;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


/**
 * Represents a Task in the entity manager.
 * It includes fields for task identification, name, description, creation date,
 * last updated date, and the user who created the task.
 *
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    /**
     * Unique identifier for the task, generated randomly.
     */
    @Id
    private String id = UUID.randomUUID().toString();

    /**
     * The name of the task.
     * The field must be unique and not be null.
     */
    @NotNull(message = "Task must have a name")
    @Column(unique = true, nullable = false)
    private String name;

    /**
     * The description of the task
     */
    private String description;

    /**
     * The local date time that the task was created.
     * It's set automatically by default with the current date and time the task ws created.
     */
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * The local date time when the task was updated (or last updated).
     */
    private LocalDateTime updatedAt;

    /**
     * The username of the logged-in user who created the task.
     */
    private String createdBy;

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
