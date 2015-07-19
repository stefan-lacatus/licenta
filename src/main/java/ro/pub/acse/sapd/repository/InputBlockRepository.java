package ro.pub.acse.sapd.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.pub.acse.sapd.model.entities.InputBlock;

/**
 * Used for getting input blocks from the database
 */
@Repository
public interface InputBlockRepository extends JpaRepository<InputBlock, Long> {

    @Query(value = "SELECT m FROM InputBlock m WHERE lower(m.name) LIKE %:searchTerm% " +
            "OR lower(m.description) LIKE %:searchTerm% ")
    Page<InputBlock> search(Pageable pageable, @Param("searchTerm") String searchTerm);
}