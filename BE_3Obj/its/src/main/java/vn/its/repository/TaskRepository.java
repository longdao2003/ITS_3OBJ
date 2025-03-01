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
    List<Task> findAllByStatus(Status status);


   
    Optional<Task> findByTitle( String title);

    @Query("SELECT t FROM Task t WHERE t.title = :title AND t.id <> :id")
    List<Task> findDuplicatesByTitleExcludingId(@Param("title") String title, @Param("id") Long id);
   

   
    



}
