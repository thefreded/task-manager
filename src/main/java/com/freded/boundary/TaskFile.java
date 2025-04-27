package com.freded.boundary;

import com.freded.control.dto.TaskFileDTO;
import com.freded.control.dto.TaskFileSortAndPaginationDTO;
import com.freded.control.dto.TaskFileUploadDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.util.List;


@Path("task/document")
@RolesAllowed({"admin", "user"})
public interface TaskFile {

    @POST
    @Path("{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public TaskFileDTO save(@PathParam("taskId") final String taskId, @MultipartForm final TaskFileUploadDTO form);

    @GET
    @Path("{taskFileId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TaskFileDTO get(@PathParam("taskFileId") final String taskFileId);

    @GET
    @Path("tasks/{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<TaskFileDTO> getAll(@PathParam("taskId") final String taskId, @BeanParam final TaskFileSortAndPaginationDTO qParams);

}
