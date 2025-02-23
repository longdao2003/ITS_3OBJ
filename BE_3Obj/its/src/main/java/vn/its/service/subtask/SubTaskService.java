package vn.its.service.subtask;


import vn.its.entity.request.SubTaskDTO;
import vn.its.entity.respone.ResponeAPI;

public interface SubTaskService {
     ResponeAPI getAllSubTask();

    ResponeAPI addSubTask(Long taskId, SubTaskDTO subTaskDTO);

    ResponeAPI editSubTask(Long taskId,Long subTaskId, SubTaskDTO subTaskDTO);

    ResponeAPI deleteSubTask(Long taskId, Long subTaskId);
}
