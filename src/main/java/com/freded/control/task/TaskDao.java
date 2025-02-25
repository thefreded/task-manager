package com.freded.control.task;

import com.freded.CustomWebApplicationException;
import com.freded.CustomLog;
import com.freded.control.service.PaginationAndSortingService;
import com.freded.control.service.UserService;
import com.freded.entity.PaginationAndSortingDTO;
import com.freded.entity.TaskDTO;
import com.freded.entity.TaskSortAndPaginationDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class TaskDao {

    private static  final String CREATEDBY = "createdBy";


    @Inject
    EntityManager em;

    @Inject
    UserService userService;

    @Inject
    PaginationAndSortingService paginationAndSortingService;

    /**
     * Adds a new task to the entity manager/database.
     *
     * @param task the {@link TaskDTO} object containing task details to be added.
     * @return the created {@link TaskDTO} .
     */
    @Transactional
    @CustomLog
    public TaskDTO addTask(final TaskDTO task){
        task.setCreatedBy(userService.getUsername());
        em.persist(task);
        return task;
    }

    /**
     * Gets all tasks created by the currently authenticated user.
     * Can also be sorted and paginated based on the provided parameters.
     *
     * @param qParams the {@link TaskSortAndPaginationDTO} containing pagination and sorting options.
     * @return a list of {@link TaskDTO} objects representing the tasks created by the user in regard to the options provided in {@code qParams}.
     */
    public List<TaskDTO> getAllTask(final TaskSortAndPaginationDTO qParams){
        // 1. Get the CriteriaBuilder.
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Create the CriteriaQuery object for Task (more like blueprint)
        CriteriaQuery<TaskDTO>  cbQuery = cb.createQuery(TaskDTO.class);

        //Define the FROM clause (Task table. Where the query should start from)
        Root<TaskDTO> root  = cbQuery.from(TaskDTO.class);


        // Create a ParameterExpression for the parameter
        ParameterExpression<String> createdByParam = cb.parameter(String.class, CREATEDBY);


        // Select the root entity (TaskDTO) and apply the filter condition to ensure the task is created by the authenticated user.
        cbQuery.select(root).where(cb.equal(root.get(CREATEDBY), createdByParam));

        // Apply sorting based on the parameters provided in qParams.
        paginationAndSortingService.sort(cb, cbQuery, root, qParams);

        // Create a TypedQuery to execute the CriteriaQuery and get the result as TaskDTO objects.
        TypedQuery<TaskDTO> typedQuery = em.createQuery(cbQuery);

        // Set the parameter for the createdBy field to the username of the currently authenticated user.
        typedQuery.setParameter(CREATEDBY, userService.getUsername());

        // Apply pagination settings to the query based on the provided qParams.
        paginationAndSortingService.paginate(typedQuery, qParams);


        return typedQuery.getResultList();
    }


    /**
     * Gets the task with the taskId provided that was created by the authenticated user.
     *
     * @param taskId id of the task.
     * @return the found task {@link TaskDTO}, or {@code null} if no task is found.
     */
    public TaskDTO getTask(final String taskId){
       return getTaskByIdAndCreatedBy((taskId));
    }

    /***
     * Updates the task with the taskId with the data provided in newTask.
     *
     * @param taskId id of the task to be updated.
     * @param newTask new task data used for the update {@link TaskDTO}.
     * @return newly updated task {@link TaskDTO} or {@code null} if no task is found.
     */
    @Transactional
    public TaskDTO updateTask( final String taskId,  final TaskDTO newTask){
       TaskDTO task = getTaskByIdAndCreatedBy(taskId);

       if(task == null){
           throw  new CustomWebApplicationException("Task with Id:" + taskId, 204);
       }

           // update task with new task inputs
           task.setName(newTask.getName());
           task.setDescription(newTask.getDescription());
           task.setUpdatedAt(LocalDateTime.now());

           em.merge(task);
           return task;


    }

    /**
     * Deletes the task with the taskId provided.
     * @param taskId id of the task to be deleted
     * @return the id of the deleted ask or {@code null} if no task is found with the id.
     */
    @Transactional
    public String deleteTask(final String taskId){
        TaskDTO task = getTaskByIdAndCreatedBy(taskId);

        em.remove(task);
        return  taskId;
    }

    /**
     * Retrieves the task by taskId, ensuring it was created by the authenticated user.
     *
     * @param taskId the id of the task.
     * @return the {@link TaskDTO} if found, or {@code null} if no task is found.
     */
    private TaskDTO getTaskByIdAndCreatedBy(final String taskId){
        String queryString =  "SELECT task FROM TaskDTO task WHERE task.id = :taskId AND task.createdBy =:createdBy";
        TypedQuery<TaskDTO> query = em.createQuery(queryString, TaskDTO.class);

        return query.setParameter("taskId", taskId).setParameter("createdBy",userService.getUsername())
                .getResultStream().findFirst().orElse(null);
    }


}
