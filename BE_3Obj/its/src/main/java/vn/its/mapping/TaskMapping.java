package vn.its.mapping;

import vn.its.entity.model.Status;
import vn.its.entity.model.Task;
import vn.its.entity.request.TaskDTO;
import vn.its.entity.respone.TaskRespone;

public class TaskMapping {
    public static Task toTask(TaskDTO taskDTO){
        return Task.builder()
        .process(taskDTO.getProcess())
        .description(taskDTO.getDescription())
        .title(taskDTO.getTitle())
        .status(Status.builder().title(taskDTO.getStatus()).build() )
        .build();
    }
    public static TaskRespone toTaskRespone(Task task){
        return TaskRespone.builder()
        .id(task.getId())
        .process(task.getProcess())
        .description(task.getDescription())
        .title(task.getTitle())
        .status(task.getStatus().getTitle())
        .build();
    }
}
