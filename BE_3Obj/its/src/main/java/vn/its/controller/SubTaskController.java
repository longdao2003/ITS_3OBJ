package vn.its.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.its.entity.request.SubTaskDTO;
import vn.its.entity.respone.ResponeAPI;
import vn.its.service.subtask.SubTaskService;

@RestController
@RequestMapping("/task/{idtask}")
public class SubTaskController {
    
    @Autowired
    SubTaskService subTaskService;

    @GetMapping("/subtask")
    public ResponseEntity<ResponeAPI> getAllSubTask(@PathVariable Long idtask) {
        return ResponseEntity.ok(subTaskService.getAllSubTask(idtask));
    }

    @PostMapping("/subtask")
    public ResponseEntity<ResponeAPI> addSubTask(@PathVariable Long idtask,@RequestBody @Valid SubTaskDTO subTaskDTO){
        return ResponseEntity.ok(subTaskService.addSubTask(idtask, subTaskDTO));
    }

    @PutMapping("/subtask/{idsubtask}") 
    public ResponseEntity<ResponeAPI> updateSubTask(@PathVariable Long idtask, @PathVariable Long idsubtask, @RequestBody @Valid SubTaskDTO subTaskDTO){
        return ResponseEntity.ok(subTaskService.editSubTask(idtask, idsubtask, subTaskDTO));
    }

    @DeleteMapping("/delete/{idsubtask}")
    public ResponseEntity<ResponeAPI> deleteStatus(@PathVariable Long idtask,  @PathVariable Long idsubtask){
        return ResponseEntity.ok(subTaskService.deleteSubTask(idtask, idsubtask));
    }    

}
