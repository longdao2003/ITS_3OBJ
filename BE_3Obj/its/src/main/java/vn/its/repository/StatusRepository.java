package vn.its.repository;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.its.entity.model.Status;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status,Long> {
   

    @Query("SELECT e FROM Status e WHERE LOWER(REPLACE(e.title, ' ', '')) = :title")
    Optional<Status> findByTitle(@Param("title") String title);

    void deleteByTitle (String title);
}
