package com.freded.boundary;

import com.freded.control.dto.TaskDTO;
import com.freded.control.dto.TaskSortAndPaginationDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;


/**
 * Manages CRUD operations performed on task.
 * This includes, creating, updating, retrieving and deleting operations.
 * Must be authenticated and have the roles admin or user to perform any of the operations.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("tasks")
@RolesAllowed({"admin", "user"})
public interface Task {

    /**
     * Creates a task
     *
     * @param task the {@link TaskDTO} object containing task details.
     * @return the created {@link TaskDTO}.
     */
    @POST
    public TaskDTO create(final TaskDTO task);

    /**
     * Retrieves all task created by the logged-in user.
     *
     * @param qParams the {@link TaskSortAndPaginationDTO} object of pagination and sorting parameters.
     * @return a list of {@link TaskDTO}.
     */
    @GET
    public List<TaskDTO> getAll(@BeanParam final TaskSortAndPaginationDTO qParams);

    /**
     * Retrieves a given task with the taskId provided.
     *
     * @param taskId id of task to be retrieved, or {@code null} if no task is found.
     * @return {@link TaskDTO}.
     */
    @GET
    @Path("{taskId}")
    public TaskDTO get(@PathParam("taskId") final String taskId);

    /**
     * Deletes a task with the taskId provided.
     *
     * @param taskId of task to be deleted.
     * @return taskId that was deleted, or {@code null} if no task is found.
     */
    @DELETE
    @Path("{taskId}")
    public String delete(@PathParam("taskId") final String taskId);

    /**
     * Updates task that has the taskId provided with the task object provided.
     *
     * @param taskId id of task to be updated.
     * @param task   {@link TaskDTO} new task object
     * @return {@link  TaskDTO} newly updated task, or {@code null} if no task is found.
     */
    @PUT
    @Path("{taskId}")
    public TaskDTO update(@PathParam("taskId") final String taskId, final TaskDTO task);

}
