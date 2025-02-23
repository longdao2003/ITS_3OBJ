package vn.its.mapping;


import vn.its.entity.model.Status;
import vn.its.entity.request.StatusDTO;
import vn.its.entity.respone.StatusRespone;

public class StatusMapping {
    public static Status toStatus(StatusDTO statusDTO){
        return Status.builder().
        title(statusDTO.getTitle()).description(statusDTO.getDescription()).build();
    }

    public static StatusRespone toStatusRespone(Status status){
        return StatusRespone.builder().id(status.getId()).title(status.getTitle()).description(status.getDescription()).build();
    }
}
