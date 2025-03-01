package vn.its.service.task;

import vn.its.entity.request.TaskDTO;
import vn.its.entity.respone.ResponeAPI;

public interface TaskService {
    ResponeAPI getAllTask();
    ResponeAPI addTask(TaskDTO taskDTO);

    ResponeAPI editTask(Long idTask, TaskDTO taskDTO);
    ResponeAPI deleteTask(Long idTask);

}
