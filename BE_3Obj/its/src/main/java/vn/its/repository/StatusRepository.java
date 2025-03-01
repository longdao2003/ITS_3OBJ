package vn.its.repository;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.its.entity.model.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status,Long> {
   

    Optional<Status> findByTitle(String title);

    @Query("SELECT s FROM Status s WHERE s.title = :title AND s.id <> :id")
    List<Status> findDuplicatesByTitleExcludingId(@Param("title") String title, @Param("id") Long id);
    

}
