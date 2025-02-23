package vn.its.service.subtask;



import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.its.entity.model.SubTask;
import vn.its.entity.model.Task;
import vn.its.entity.request.SubTaskDTO;
import vn.its.entity.respone.ResponeAPI;
import vn.its.exception.DataExistException;
import vn.its.exception.DataNotFoundException;
import vn.its.mapping.SubTaskMapping;
import vn.its.repository.SubTaskRepository;
import vn.its.repository.TaskRepository;

@Service
public class SubTaskServiceImpl implements SubTaskService {
    
    @Autowired 
    SubTaskRepository subTaskRepository;
    
    @Autowired
    TaskRepository taskRepository;
    public ResponeAPI getAllSubTask(){
        return ResponeAPI.builder()
        .status(true)
        .message("Get All SubTask Data Success")
        .data(subTaskRepository.findAll().stream().map(SubTaskMapping::toSubTaskRespone).toList())
        .build();

    }

    public ResponeAPI addSubTask(Long taskId, SubTaskDTO subTaskDTO){
        
        taskRepository.findById(taskId)
        .orElseThrow(() ->  new DataNotFoundException("Task is not exist"));


        
        subTaskRepository.findByTitle(subTaskDTO.getTitle().trim().toLowerCase())
        .ifPresent((data) -> new DataExistException("SubTask is already exists"));

        if ((subTaskDTO.getProcess() == 100 && subTaskDTO.getStatus().trim().toLowerCase() != "hoànthành") 
        ||  (subTaskDTO.getProcess() != 100 && subTaskDTO.getStatus().trim().toLowerCase() == "hoànthành")){
            throw new  IllegalArgumentException("Process and Status illegal") ;
        }

       
        SubTask subTask=SubTaskMapping.toSubTask(subTaskDTO);
        subTaskRepository.save(subTask);
        

        Stream<SubTask> streamSubTask=  subTaskRepository.findByTaskIdJPQL(taskId).stream();
        Task task=taskRepository.findById(taskId).get();
        task.setProcess((int) (Math.round( (double) streamSubTask.filter(data -> data.getStatus().getTitle().equals("Hoàn thành")).count() / streamSubTask.count())));

        taskRepository.save(task);
        
        return null;
        

    }

    public ResponeAPI editSubTask(Long taskId,Long subTaskId, SubTaskDTO subTaskDTO){
        taskRepository.findById(taskId)
        .orElseThrow(() ->  new DataNotFoundException("Task is not exist"));
        
        subTaskRepository.findById(subTaskId).orElseThrow(() -> new DataNotFoundException("SubTask is not exist"));

        subTaskRepository.findByTitle(subTaskDTO.getTitle().trim().toLowerCase())
        .ifPresent((data) -> new DataExistException("SubTask title mustn't duplicate"));


        if ((subTaskDTO.getProcess() == 100 && subTaskDTO.getStatus().trim().toLowerCase() != "hoànthành") 
        ||  (subTaskDTO.getProcess() != 100 && subTaskDTO.getStatus().trim().toLowerCase() == "hoànthành")){
            throw new  IllegalArgumentException("Process and Status illegal") ;
        }

        SubTask subTask=SubTaskMapping.toSubTask(subTaskDTO);
        subTask.setId(subTaskId);
        subTaskRepository.save(subTask);

        
        Stream<SubTask> streamSubTask=  subTaskRepository.findByTaskIdJPQL(taskId).stream();
        Task task=taskRepository.findById(taskId).get();
        task.setProcess((int) (Math.round( (double) streamSubTask.filter(data -> data.getStatus().getTitle().equals("Hoàn thành")).count() / streamSubTask.count())));

        return null;
    }

    public ResponeAPI deleteSubTask(Long subTaskId, Long taskId){
        subTaskRepository.findById(subTaskId).orElseThrow(() -> new DataNotFoundException("SubTask is not exist"));

        taskRepository.findById(taskId)
        .orElseThrow(() ->  new DataNotFoundException("Task is not exist"));

        subTaskRepository.deleteById(subTaskId);

        Stream<SubTask> streamSubTask=  subTaskRepository.findByTaskIdJPQL(taskId).stream();
        Task task=taskRepository.findById(taskId).get();
        task.setProcess((int) (Math.round( (double) streamSubTask.filter(data -> data.getStatus().getTitle().equals("Hoàn thành")).count() / streamSubTask.count())));


        return null;
    }
}
