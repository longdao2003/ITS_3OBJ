package vn.its.service.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import vn.its.entity.model.Status;
import vn.its.entity.model.SubTask;
import vn.its.entity.model.Task;
import vn.its.entity.request.TaskDTO;
import vn.its.entity.respone.ResponeAPI;
import vn.its.exception.ApiException;
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


@Override
public ResponeAPI getAllTask() {
    return ResponeAPI.builder()
    .status(true)
    .message("Get All Task Data Success")
    .data(taskRepository.findAll().stream().map(TaskMapping::toTaskRespone).toList())
    .build();
}


@Override
public ResponeAPI addTask(TaskDTO taskDTO) {

    
    Task t=TaskMapping.toTask(taskDTO);
    t.setStatus(statusRepository.findById(taskDTO.getStatus()).get());
    try{
        taskRepository.save(t);
    }
    catch(Exception e){
        throw new ApiException("Task thêm mới đã tồn tại");
    }
    
   
    return ResponeAPI.builder()
    .status(true)
    .message("Add Task Data Success")
    .data(null)
    .build();
}


@Override
public ResponeAPI editTask(Long idTask, TaskDTO taskDTO) {
    
    if(!taskRepository.findById(idTask).isPresent()){
        throw new ApiException("Task cần sửa không tồn tại");
    }
    Task t=TaskMapping.toTask(taskDTO);
    t.setStatus(statusRepository.findById(taskDTO.getStatus()).get());
    t.setId(idTask);
    try{
        taskRepository.save(t);
    }
    catch(Exception e){
        throw new ApiException("Task thêm mới đã tồn tại");
    }
    Status statusHoanThanh=statusRepository.findById(1l).get();
    Status statusKhongHoanThanh=statusRepository.findById(2l).get();
    List<SubTask> subTask=subTaskRepository.findAllByTask(t);
   
    if (taskDTO.getProcess()==100){
        subTask=subTask.stream().map(data -> { 
            data.setStatus(statusHoanThanh);
            data.setProcess(100);
            return data;
        }).toList();
    }
    else{
        subTask=subTask.stream().filter(data -> data.getStatus().equals(statusKhongHoanThanh)).map(data -> { 
            data.setStatus(statusKhongHoanThanh);
            data.setProcess(99);
            return data;
        }).toList();
    }

    

    subTaskRepository.saveAll(subTask);

    return ResponeAPI.builder()
    .status(true)
    .message("Edit Task Data Success")
    .data(null)
    .build();
}


@Override
public ResponeAPI deleteTask(Long idTask) {
    if(!taskRepository.findById(idTask).isPresent()){
        throw new ApiException("Task cần xóa không tồn tại");
    }
    taskRepository.deleteById(idTask);
    
    return ResponeAPI.builder()
    .status(true)
    .message("Delete Task Data Success")
    .data(null)
    .build();
}
   
   
}
