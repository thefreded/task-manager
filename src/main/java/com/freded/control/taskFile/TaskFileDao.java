package com.freded.control.taskFile;

import com.freded.CustomWebApplicationException;
import com.freded.control.service.PaginationAndSortingService;
import com.freded.control.service.UserService;
import com.freded.control.task.TaskDao;
import com.freded.entity.PaginationAndSortingDTO;
import com.freded.entity.TaskFileDTO;
import com.freded.entity.TaskFileSortAndPaginationDTO;
import com.freded.entity.TaskFileUploadDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TaskFileDao {

    @Inject
    EntityManager em;

    @Inject
    TaskDao taskDao;

    @Inject
    UserService userService;

    @Inject
    PaginationAndSortingService paginationAndSortingService;

    @Transactional
    public TaskFileDTO saveTaskFile(final String taskId, final TaskFileUploadDTO form){
        String fileName = form.getFileName();
        String fileType = form.getFileType();
        byte[] fileData = form.getFileData();

        // if no file, then it is a bad request
        if (fileData == null || fileData.length == 0) {
            throw new CustomWebApplicationException("File is missing", 400);
        }

        // if no fileName, then it is a bad request
        if (fileName.isBlank()) {
            throw new CustomWebApplicationException("File name is missing", 400);
        }


        // if no task with the id assigned to the user, then it is a bad request
        if(taskDao.getTask(taskId) == null){
            throw new CustomWebApplicationException("Wrong taskId", 400);
        }

        TaskFileDTO taskFile = new TaskFileDTO();
        taskFile.setFileName(fileName);
        taskFile.setFileType(fileType);
        taskFile.setFileData(fileData);
        taskFile.setTaskId(taskId);
        taskFile.setUploadedBy(userService.getUsername());

        em.persist(taskFile);

        return taskFile;
    }

   public TaskFileDTO getTaskFile(final String taskFileId){

       String queryString =  "SELECT taskFile FROM TaskFileDTO taskFile WHERE taskFile.id = :taskFileId AND taskFile.uploadedBy =:uploadedBy";
       TypedQuery<TaskFileDTO> query = em.createQuery(queryString, TaskFileDTO.class);

       return query.setParameter("taskFileId", taskFileId).setParameter("uploadedBy",userService.getUsername())
               .getResultStream().findFirst().orElse(null);
    }

    public List<TaskFileDTO> getAllTaskFiles(final String taskId, final TaskFileSortAndPaginationDTO qParams){

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<TaskFileDTO>  cbQuery = cb.createQuery(TaskFileDTO.class);

        Root<TaskFileDTO> root = cbQuery.from(TaskFileDTO.class);

        ParameterExpression<String> taskIdParam = cb.parameter(String.class, "taskId");
        ParameterExpression<String> uploadedByParam = cb.parameter(String.class, "uploadedBy");


        cbQuery.select(root).where(
                cb.and(
                        cb.equal(root.get("taskId"), taskIdParam),
                        cb.equal(root.get("uploadedBy"), uploadedByParam)
                )
        );

        // Apply sorting based on the parameters provided in qParams.
        paginationAndSortingService.sort(cb, cbQuery, root, qParams);

        TypedQuery<TaskFileDTO> typedQuery = em.createQuery(cbQuery);

        typedQuery.setParameter("uploadedBy", userService.getUsername());
        typedQuery.setParameter("taskId", taskId);

        paginationAndSortingService.paginate(typedQuery, qParams);


          return typedQuery.getResultList();
    }


}
