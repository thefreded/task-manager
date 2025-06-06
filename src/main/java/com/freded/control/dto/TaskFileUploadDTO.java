package com.freded.control.dto;

import jakarta.ws.rs.FormParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskFileUploadDTO {

    @FormParam("fileName")
    @PartType("text/plain")
    private String fileName;

    @FormParam("fileType")
    @PartType("text/plain")
    private String fileType;

    @FormParam("file")
    @PartType("application/octet-stream")
    private byte[] fileData;
}
