package vn.its.mapping;

import vn.its.entity.model.Status;
import vn.its.entity.model.SubTask;
import vn.its.entity.request.SubTaskDTO;
import vn.its.entity.respone.SubTaskRespone;

public class SubTaskMapping {
    public static SubTask toSubTask(SubTaskDTO subTaskDTO){
        return SubTask.builder()
        .title(subTaskDTO.getTitle())
        .status(Status.builder().title(subTaskDTO.getStatus()).build() )
        .process(subTaskDTO.getProcess())
 
        .description(subTaskDTO.getDescription())
        .build();
    }

    public static SubTaskRespone toSubTaskRespone(SubTask subTask){
        return SubTaskRespone.builder()
        .id(subTask.getId())
        .title(subTask.getTitle())
        .status(subTask.getStatus().getTitle())
        .process(subTask.getProcess())

        .description(subTask.getDescription())
        .build();
    }
}
