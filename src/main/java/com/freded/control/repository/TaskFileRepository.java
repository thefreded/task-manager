package com.freded.control.repository;

import com.freded.control.service.PaginationAndSortingService;
import com.freded.control.service.TaskService;
import com.freded.control.service.UserService;
import com.freded.entity.TaskFileEntity;
import com.freded.control.dto.TaskFileSortAndPaginationDTO;
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
public class TaskFileRepository {

    @Inject
    EntityManager em;

    @Inject
    TaskService taskService;

    @Inject
    UserService userService;

    @Inject
    PaginationAndSortingService paginationAndSortingService;

    @Transactional
    public TaskFileEntity save(final TaskFileEntity taskFileEntity) {
        em.persist(taskFileEntity);

        return taskFileEntity;
    }

    public TaskFileEntity read(final String uploadedBy, final String taskFileId) {

        String queryString = "SELECT taskFile FROM TaskFileEntity taskFile WHERE taskFile.id = :taskFileId AND taskFile.uploadedBy =:uploadedBy";
        TypedQuery<TaskFileEntity> query = em.createQuery(queryString, TaskFileEntity.class);

        return query.setParameter("taskFileId", taskFileId).setParameter("uploadedBy", uploadedBy).getResultStream().findFirst().orElse(null);
    }

    public List<TaskFileEntity> readAll(final String uploadedBy, final String taskId, final TaskFileSortAndPaginationDTO qParams) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<TaskFileEntity> cbQuery = cb.createQuery(TaskFileEntity.class);

        Root<TaskFileEntity> root = cbQuery.from(TaskFileEntity.class);

        ParameterExpression<String> taskIdParam = cb.parameter(String.class, "taskId");
        ParameterExpression<String> uploadedByParam = cb.parameter(String.class, "uploadedBy");


        cbQuery.select(root).where(cb.and(cb.equal(root.get("taskId"), taskIdParam), cb.equal(root.get("uploadedBy"), uploadedByParam)));

        // Apply sorting based on the parameters provided in qParams.
        paginationAndSortingService.sort(cb, cbQuery, root, qParams);

        TypedQuery<TaskFileEntity> typedQuery = em.createQuery(cbQuery);

        typedQuery.setParameter("uploadedBy", uploadedBy);
        typedQuery.setParameter("taskId", taskId);

        paginationAndSortingService.paginate(typedQuery, qParams);


        return typedQuery.getResultList();
    }


}
