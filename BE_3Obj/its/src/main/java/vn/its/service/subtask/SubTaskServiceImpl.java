package vn.its.service.subtask;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.its.entity.model.Status;
import vn.its.entity.model.SubTask;
import vn.its.entity.model.Task;
import vn.its.entity.request.SubTaskDTO;
import vn.its.entity.respone.ResponeAPI;
import vn.its.exception.ApiException;
import vn.its.mapping.SubTaskMapping;
import vn.its.repository.StatusRepository;
import vn.its.repository.SubTaskRepository;
import vn.its.repository.TaskRepository;

@Service
public class SubTaskServiceImpl implements SubTaskService {
    
    @Autowired 
    SubTaskRepository subTaskRepository;
    
    @Autowired
    TaskRepository taskRepository;

    @Autowired 
    StatusRepository statusRepository;
    
    @Override
    public ResponeAPI getAllSubTask(Long taskId) {
        
        if(!taskRepository.findById(taskId).isPresent()){
            throw new ApiException("Task không tồn tại");
        }
        Task task=taskRepository.findById(taskId).get();
        
        return  ResponeAPI.builder().status(true).message("Get All SubTask Success")
        .data(subTaskRepository.findAllByTask(task).stream().map(SubTaskMapping::toSubTaskRespone).toList()).build(); 
    }

    @Override
    public ResponeAPI addSubTask(Long taskId, SubTaskDTO subTaskDTO) {
        if(!taskRepository.findById(taskId).isPresent()){
            throw new ApiException("Task không tồn tại");
        }
        Task task=taskRepository.findById(taskId).get();
        SubTask subTask=SubTaskMapping.toSubTask(subTaskDTO);
        Status statusHoanThanh=statusRepository.findById(1l).get();
        Status statusKhongHoanThanh=statusRepository.findById(2l).get();
        List<SubTask> listSubTaskIsChildOfTask=subTaskRepository.findAllByTask(task);

        for (var t: listSubTaskIsChildOfTask){

            if (t.getTitle().equals(subTaskDTO.getTitle())){
                throw new ApiException("SubTask này đã tồn tại trong task");
            }
        }
        subTask.setTask(task);
        subTask.setStatus(statusRepository.findById(subTaskDTO.getIdStatus()).get());
        subTaskRepository.save(subTask);

        Long countHoanThanhSubTask=listSubTaskIsChildOfTask.stream().filter(data -> data.getProcess()==(100)).count();
        System.out.println(countHoanThanhSubTask);
        task.setProcess((int)( countHoanThanhSubTask *100 / listSubTaskIsChildOfTask.size()));
        System.out.println(task.getProcess());
       if (task.getProcess()==100){
        task.setStatus(statusHoanThanh);
       }
        else if(task.getProcess()!=100 && task.getStatus()==statusHoanThanh){
            task.setStatus(statusKhongHoanThanh);
       } 
       taskRepository.save(task);
        return ResponeAPI.builder().status(true).message("Add SubTask Success").data(null).build();
    }

    @Override
    public ResponeAPI editSubTask(Long taskId, Long subTaskId, SubTaskDTO subTaskDTO) {

        if(!taskRepository.findById(taskId).isPresent()){
            throw new ApiException("Task không tồn tại");
        }
        if (!subTaskRepository.findById(subTaskId).isPresent()){
            throw new ApiException("SubTask cần sửa không tồn tại");
        }

        Task task=taskRepository.findById(taskId).get();
        SubTask subTask=SubTaskMapping.toSubTask(subTaskDTO);
        Status statusHoanThanh=statusRepository.findById(1l).get();
        Status statusKhongHoanThanh=statusRepository.findById(2l).get();
        List<SubTask> listSubTaskIsChildOfTask=subTaskRepository.findAllByTask(task);
       
        for (var t: listSubTaskIsChildOfTask){

            if (t.getTitle().equals(subTaskDTO.getTitle()) && t.getId()!=subTaskId){
                throw new ApiException("SubTask này đã tồn tại trong task");
            }
        }

        subTask.setId(subTaskId);
        subTask.setTask(task);
        subTask.setStatus(statusRepository.findById(subTaskDTO.getIdStatus()).get());
        subTaskRepository.save(subTask);

        Long countHoanThanhSubTask=listSubTaskIsChildOfTask.stream().filter(data -> data.getProcess()==(100)).count();
        System.out.println(countHoanThanhSubTask);
        task.setProcess((int)( countHoanThanhSubTask *100 / listSubTaskIsChildOfTask.size()));
        System.out.println(task.getProcess());
        if (task.getProcess()==100){
        task.setStatus(statusHoanThanh);
       }
        else if(task.getProcess()!=100 && task.getStatus()==statusHoanThanh){
            task.setStatus(statusKhongHoanThanh);
       } 
       taskRepository.save(task);
       
        return ResponeAPI.builder().status(true).message("Update SubTask Success").data(null).build();
    }

    @Override
    public ResponeAPI deleteSubTask(Long taskId, Long subTaskId) {
        if(!taskRepository.findById(taskId).isPresent()){
            throw new ApiException("Task không tồn tại");
        }
        if (!subTaskRepository.findById(subTaskId).isPresent()){
            throw new ApiException("SubTask cần xóa không tồn tại");
        }
        
        subTaskRepository.delete(subTaskRepository.findById(subTaskId).get());
        Task task=taskRepository.findById(taskId).get();
        Status statusHoanThanh=statusRepository.findById(1l).get();
        Status statusKhongHoanThanh=statusRepository.findById(2l).get();
        List<SubTask> listSubTaskIsChildOfTask=subTaskRepository.findAllByTask(task);

        Long countHoanThanhSubTask=listSubTaskIsChildOfTask.stream().filter(data -> data.getProcess()==(100)).count();

        task.setProcess((int)( countHoanThanhSubTask *100 / listSubTaskIsChildOfTask.size()));
        if (task.getProcess()==100){
            task.setStatus(statusHoanThanh);
           }
            else if(task.getProcess()!=100 && task.getStatus()==statusHoanThanh){
                task.setStatus(statusKhongHoanThanh);
           }
           taskRepository.save(task);

           return ResponeAPI.builder().status(true).message("Delete SubTask Success").data(null).build();
        
    }
}
