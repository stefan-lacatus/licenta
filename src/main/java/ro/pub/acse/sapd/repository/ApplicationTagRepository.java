package ro.pub.acse.sapd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.pub.acse.sapd.model.entities.ApplicationTag;

/**
 * Used for getting application tags from the database
 */
@Repository
public interface ApplicationTagRepository extends JpaRepository<ApplicationTag, Long> {
}