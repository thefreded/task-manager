package com.freded.control.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskFileDTO {

    private String id;

    private String fileName;

    private String fileType;


    private LocalDateTime uploadedAt;




}
