package vn.its.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.its.entity.model.Status;

import vn.its.entity.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(Status status);


    @Query("SELECT e FROM Task e WHERE LOWER(REPLACE(e.title, ' ', '')) = :title")
    Optional<Task> findByTitle(@Param("title") String title);
   
    



}
