package vn.its.service.task;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import vn.its.entity.model.Status;
import vn.its.entity.model.Task;
import vn.its.entity.request.TaskDTO;
import vn.its.entity.respone.ResponeAPI;
import vn.its.exception.DataExistException;
import vn.its.mapping.TaskMapping;
import vn.its.repository.StatusRepository;
import vn.its.repository.SubTaskRepository;
import vn.its.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
   
   @Autowired
   TaskRepository taskRepository;
    
   @Autowired
   SubTaskRepository subTaskRepository;


   @Autowired
   StatusRepository statusRepository;
   
   public ResponeAPI getAllTask(){
    return ResponeAPI.builder()
    .status(true)
    .message("Get All Task Data Success")
    .data(taskRepository.findAll().stream().map(TaskMapping::toTaskRespone).toList())
    .build();
   }
   public ResponeAPI addTask(TaskDTO taskDTO){
    
    taskRepository.findByTitle(taskDTO.getTitle().trim().toLowerCase())
    .ifPresent(data -> new DataExistException("Task is exist"));
    
    if ((taskDTO.getProcess() == 100 && taskDTO.getStatus().trim().toLowerCase() != "hoànthành") 
    ||  (taskDTO.getProcess() != 100 && taskDTO.getStatus().trim().toLowerCase() == "hoànthành")){
        throw new  IllegalArgumentException("Process and Status illegal") ;
    }

    taskRepository.save(TaskMapping.toTask(taskDTO));
    return null;
};

   public ResponeAPI editTask(Long id, TaskDTO taskDTO){
    taskRepository.findByTitle(taskDTO.getTitle().trim().toLowerCase())
    .ifPresent(data -> new DataExistException("Task must not duplicated"));


    if ((taskDTO.getProcess() == 100 && taskDTO.getStatus().trim().toLowerCase() != "hoànthành") 
    ||  (taskDTO.getProcess() != 100 && taskDTO.getStatus().trim().toLowerCase() == "hoànthành")){
        throw new  IllegalArgumentException("Process and Status illegal") ;
    }
    
    
    if (!subTaskRepository.findByTaskIdJPQL(id).isEmpty()){
        Status s = statusRepository.findById(1l).get();
        if (taskDTO.getStatus().trim().toLowerCase() == "hoànthành" ){
            subTaskRepository.saveAll(subTaskRepository.findByTaskIdJPQL(id).stream().peek(data -> {
              data.setProcess(100);
              data.setStatus(s);
          }).toList()) ;
          }
        else if (taskDTO.getStatus().trim().toLowerCase() == "chưahoànthành" ){
            subTaskRepository.saveAll(subTaskRepository.findByTaskIdJPQL(id).stream().
            filter(data -> data.getStatus()==s).peek(data -> {
              data.setProcess(99);
              data.setStatus(statusRepository.findById(2l).get());
          }).toList()) ;
          }
    }
    Task task=TaskMapping.toTask(taskDTO);
    task.setId(id);
    taskRepository.save(task);
  
    return null;
};
   public ResponeAPI deleteTask(Long id){return null;};
}
