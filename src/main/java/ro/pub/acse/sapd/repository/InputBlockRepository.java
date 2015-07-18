package ro.pub.acse.sapd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.pub.acse.sapd.model.entities.InputBlock;

/**
 * Used for getting input blocks from the database
 */
@Repository
public interface InputBlockRepository extends JpaRepository<InputBlock, Long> {
}