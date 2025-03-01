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

import vn.its.entity.request.TaskDTO;
import vn.its.entity.respone.ResponeAPI;

import vn.its.service.task.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {
    
    
    @Autowired
    TaskService taskService;

    @GetMapping("/task")
    public ResponseEntity<ResponeAPI> getAllSubTask() {
        return ResponseEntity.ok(taskService.getAllTask());
    }

    @PostMapping("/task")
    public ResponseEntity<ResponeAPI> addSubTask(@RequestBody @Valid TaskDTO taskDTO){
        return ResponseEntity.ok(taskService.addTask(taskDTO));
    }

    @PutMapping("/task/{idtask}") 
    public ResponseEntity<ResponeAPI> updateSubTask(@PathVariable Long idtask, @RequestBody @Valid TaskDTO taskDTO){
        return ResponseEntity.ok(taskService.editTask(idtask, taskDTO));
    }

    @DeleteMapping("/delete/{idtask}")
    public ResponseEntity<ResponeAPI> deleteStatus(@PathVariable Long idtask){
        return ResponseEntity.ok(taskService.deleteTask(idtask));
    }    

}
