package vn.its.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.its.entity.model.Status;
import vn.its.entity.model.SubTask;


@Repository
public interface SubTaskRepository extends JpaRepository<SubTask,Long> {
    List<SubTask> findByStatus(Status status);

    @Query("SELECT e FROM SubTask e WHERE LOWER(REPLACE(e.title, ' ', '')) = :title")
    Optional<SubTask> findByTitle(@Param("title") String title);

    @Query("SELECT s FROM SubTask s WHERE s.task.id = :taskId")
    List<SubTask> findByTaskIdJPQL(@Param("taskId") Long taskId);

    

   
}
