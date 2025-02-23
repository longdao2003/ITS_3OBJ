package vn.its.service.status;

import vn.its.entity.request.StatusDTO;
import vn.its.entity.respone.ResponeAPI;
public interface StatusService {
    
    ResponeAPI getAllStatus();

    ResponeAPI addStatus(StatusDTO statusDTO);

    ResponeAPI editStatus(String title, StatusDTO statusDTO);

    ResponeAPI deleteStatus(String title);


} 
