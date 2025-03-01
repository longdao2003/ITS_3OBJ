package vn.its.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.its.entity.request.StatusDTO;
import vn.its.entity.respone.ResponeAPI;

import vn.its.service.status.StatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@CrossOrigin
@Validated
@RequestMapping("/status")
public class StatusController {
    
    @Autowired
    StatusService statusService;

    @GetMapping("")
    public ResponseEntity<ResponeAPI> getAllStatus() {
        return  ResponseEntity.ok(statusService.getAllStatus());
    }

    @PostMapping("")
    public ResponseEntity<ResponeAPI> addStatus( @RequestBody @Valid StatusDTO statusDTO){
        return ResponseEntity.ok(statusService.addStatus(statusDTO));
    }

    @PutMapping("/update/{statusId}") 
    public ResponseEntity<ResponeAPI> updateStatus(@PathVariable Long statusId, @RequestBody @Valid StatusDTO statusDTO){
        return ResponseEntity.ok(statusService.editStatus(statusId,statusDTO));
    }

    @DeleteMapping("/delete/{statusID}")
    public ResponseEntity<ResponeAPI> deleteStatus(@PathVariable Long statusID){
        return ResponseEntity.ok(statusService.deleteStatus(statusID));
    }    


}
