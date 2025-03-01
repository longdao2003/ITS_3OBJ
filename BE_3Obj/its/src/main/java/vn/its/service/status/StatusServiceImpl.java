package vn.its.service.status;


import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.its.entity.model.Status;
import vn.its.entity.model.SubTask;
import vn.its.entity.model.Task;
import vn.its.entity.request.StatusDTO;
import vn.its.entity.respone.ResponeAPI;
import vn.its.exception.ApiException;
import vn.its.mapping.StatusMapping;
import vn.its.repository.StatusRepository;
import vn.its.repository.SubTaskRepository;
import vn.its.repository.TaskRepository;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService{
    
   
    private final StatusRepository statusRepository;

    
    private final TaskRepository taskRepository;
    
    
    private final SubTaskRepository subTaskRepository;

    @Override
    public ResponeAPI getAllStatus(){
        return ResponeAPI.builder()
        .status(true)
        .message("Get All Status Data Success")
        .data(statusRepository.findAll().stream().map(StatusMapping::toStatusRespone).toList())
        .build();
    }

    @Override
    public ResponeAPI addStatus(StatusDTO statusDTO) {
        Status s=StatusMapping.toStatus(statusDTO);
        try{
            statusRepository.save(s);    
        }
        catch(Exception e){
                throw new ApiException("Status này đã tồn tại trong bảng");
        }
        
        return ResponeAPI.builder().status(true).message("Add Status Success").data(null).build();
    }

    @Override
    public ResponeAPI editStatus(Long id, StatusDTO statusDTO) {
        statusRepository.findById(id).orElseThrow(() -> new ApiException("Status cần cập nhật không tồn tại"));

        if (id==1 || id==2){
            throw new ApiException("Không thể chỉnh sửa trường Hoàn thành và Chưa hoàn thành") ;
        }

        String titleUpdate=statusDTO.getTitle(); 
        if (!statusRepository.findDuplicatesByTitleExcludingId(titleUpdate, id).isEmpty()){
            throw new ApiException("Đã có Status khác trùng giá trị title ");
        }
        
        Status s=StatusMapping.toStatus(statusDTO);
        s.setId(id);
        try{
            statusRepository.save(s);
        }
        catch(Exception e){
            throw new ApiException("Các trường status mới đã tồn tại trong bảng");
        }
        return ResponeAPI.builder().status(true).message("Edit Status Success").data(null).build();
    }

    @Override
    public ResponeAPI deleteStatus(Long id){
        if (!statusRepository.existsById(id)){
            throw new ApiException("Status cần xóa không tồn tại");
        }

        if (id==1 || id==2){
            throw new ApiException("Không thể chỉnh sửa trường Hoàn thành và Chưa hoàn thành") ;
        }
        Status s=statusRepository.findById(id).get();
        Status statusKhongHoanThanh=statusRepository.findById(2l).get();
        
        List<SubTask> subTask=subTaskRepository.findAllByStatus(s);
        List<SubTask> subTaskChange=subTask.stream().peek(data -> data.setStatus(statusKhongHoanThanh)).toList();
        subTaskRepository.saveAll(subTaskChange);
        
        List<Task> task=taskRepository.findAllByStatus(s);
        List<Task> taskChange=task.stream().peek(data -> data.setStatus(statusKhongHoanThanh)).toList();
        taskRepository.saveAll(taskChange);
        
        
        statusRepository.deleteById(id);
        System.out.println("Hello babe");
        return ResponeAPI.builder().status(true).message("Delete Status Success").data(null).build();
    }
}
