package vn.its.service.status;


import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.its.entity.model.Status;
import vn.its.entity.request.StatusDTO;
import vn.its.entity.respone.ResponeAPI;
import vn.its.exception.DataExistException;
import vn.its.exception.DataNotFoundException;
import vn.its.mapping.StatusMapping;
import vn.its.repository.StatusRepository;
import vn.its.repository.SubTaskRepository;
import vn.its.repository.TaskRepository;

@Service
public class StatusServiceImpl implements StatusService{
    
    @Autowired
    StatusRepository statusRepository;

    @Autowired
    TaskRepository taskRepository;
    
    @Autowired
    SubTaskRepository subTaskRepository;

    public ResponeAPI getAllStatus(){
        return ResponeAPI.builder()
        .status(true)
        .message("Get All Status Data Success")
        .data(statusRepository.findAll().stream().map(StatusMapping::toStatusRespone).toList())
        .build();
    }

    public ResponeAPI addStatus(StatusDTO statusDTO) throws DataExistException{
        statusRepository.findByTitle(statusDTO.getTitle().trim().toLowerCase())
        .ifPresent(task -> new DataExistException("Status Title Exist"));

        statusRepository.save(StatusMapping.toStatus(statusDTO));


        return ResponeAPI.builder().status(true).message("Add Status Success").data(null).build();

    }

    public ResponeAPI editStatus(String title, StatusDTO statusDTO) throws DataExistException,DataNotFoundException,IllegalArgumentException{
        statusRepository.findByTitle(statusDTO.getTitle().trim().toLowerCase())
        .ifPresent((data) -> new DataExistException("Update Title Existed"));
       
        statusRepository.findByTitle(title.trim().toLowerCase())
        .orElseThrow(() -> new DataNotFoundException("Status is not exitst"));
       
        if (statusDTO.getTitle().trim().toLowerCase() == "hoànthành" ||  statusDTO.getTitle().trim().toLowerCase() == "khônghoànthành" ){
            throw new IllegalArgumentException("Cannot update Hoan thanh or Khong hoan thanh data");
        }
        
        
        Status s=statusRepository.findByTitle(title.trim().toLowerCase()).get();
        s.setTitle(statusDTO.getTitle());
        s.setDescription(statusDTO.getDescription());
        
        statusRepository.save(s);
        return ResponeAPI.builder().status(true).message("Edit Status Success").data(null).build();
    
    
    }

    public ResponeAPI deleteStatus(String title) throws DataExistException,DataNotFoundException{
        
        if (title.trim().toLowerCase() == "hoànthành" ||  title.trim().toLowerCase() == "khônghoànthành" ){
            throw new IllegalArgumentException("Cannot delete Hoan thanh or Khong hoan thanh data");
        }
         
        statusRepository.findByTitle(title.trim().toLowerCase())
        .orElseThrow(() -> new DataNotFoundException("Status Title isnot Existed"));
        

        taskRepository.saveAll(taskRepository.findByStatus(statusRepository.findByTitle(title).get())
        .stream().peek(data -> data.setTitle("Chưa hoàn thành")).collect(Collectors.toList()));
        // Status in Task and SubTask change
        subTaskRepository.saveAll(subTaskRepository.findByStatus(statusRepository.findByTitle(title).get())
        .stream().peek(data -> data.setTitle("Chưa hoàn thành")).collect(Collectors.toList()));
        

        statusRepository.deleteByTitle(title);
        return ResponeAPI.builder().status(true).message("Delete Status Success").data(null).build();

    }
}
