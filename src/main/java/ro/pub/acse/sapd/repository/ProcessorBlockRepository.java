package ro.pub.acse.sapd.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.pub.acse.sapd.model.entities.ProcessorBlock;

/**
 * Used for getting input channels from the database
 */
@Repository
public interface ProcessorBlockRepository extends JpaRepository<ProcessorBlock, Long> {
    @Query(value = "SELECT m FROM ProcessorBlock m WHERE lower(m.name) LIKE %:searchTerm% ")
    Page<ProcessorBlock> search(Pageable pageable, @Param("searchTerm") String searchTerm);
}