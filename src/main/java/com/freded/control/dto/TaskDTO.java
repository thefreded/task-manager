package com.freded.control.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * DTO for task
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    /**
     * Unique identifier for the task.
     */
    private String id;

    /**
     * The name of the task.
     */
    private String name;

    /**
     * The description of the task
     */
    private String description;

    /**
     * The local date time that the task was created.
     */
    private LocalDateTime createdAt;

    /**
     * The local date time when the task was updated (or last updated).
     */
    private LocalDateTime updatedAt;


}
