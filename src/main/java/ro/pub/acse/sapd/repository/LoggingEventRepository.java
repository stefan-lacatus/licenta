package ro.pub.acse.sapd.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.pub.acse.sapd.model.entities.logback.LoggingEvent;

/**
 * Used for getting application tags from the database
 */
@Repository
public interface LoggingEventRepository extends JpaRepository<LoggingEvent, Long> {
    @Query(value = "SELECT m FROM LoggingEvent m WHERE lower(m.formattedMessage) LIKE %:searchTerm%" +
            " OR lower(m.loggerName) LIKE %:searchTerm%")
    Page<LoggingEvent> search(Pageable pageable, @Param("searchTerm") String searchTerm);
}