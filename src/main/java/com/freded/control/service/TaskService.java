package com.freded.control.service;

import com.freded.entity.TaskDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * First task service implementation that persist task within application running cycle.
 * Moved to TaskDao now.
 */
@ApplicationScoped
public class TaskService {
    final private List<TaskDTO> tasks = new ArrayList<>() ;


    public TaskDTO addTask(final TaskDTO task){
        tasks.add(task);
        return task;
    }

    public List<TaskDTO> getAllTask(){
        return  tasks;
    }

    public TaskDTO getTask(final String taskId){
        return tasks.stream().filter( task -> task.getId().equals(taskId)).findFirst().orElse(null);
    }

    public TaskDTO updateTask( final String taskId,  final TaskDTO newTask){
      TaskDTO updatedTask =  tasks.stream().filter( task -> task.getId().equals(taskId)).peek(task -> {
            task.setName(newTask.getName());
            task.setDescription(newTask.getDescription());
            task.setUpdatedAt(LocalDateTime.now());
        }).findFirst().orElse(null);

      return updatedTask;
    }

    public String deleteTask(final String taskId){
        long initialSize = tasks.size();
        tasks.removeIf(task -> task.getId().equals(taskId));
        if(tasks.size() < initialSize){
            return taskId;
        }
        return null;
    }

}
