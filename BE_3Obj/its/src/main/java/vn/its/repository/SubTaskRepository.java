package vn.its.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import vn.its.entity.model.Status;
import vn.its.entity.model.SubTask;


@Repository
public interface SubTaskRepository extends JpaRepository<SubTask,Long> {
    List<SubTask> findAllByStatus(Status status);


    Optional<SubTask> findByTitle(String title);

    List<SubTask> findAllByTask(vn.its.entity.model.Task task);

    

   
}
