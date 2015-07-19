package ro.pub.acse.sapd.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.pub.acse.sapd.model.entities.ApplicationUser;

/**
 * Used for getting users out of the database
 */
@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);

    @Query(value = "SELECT m FROM ApplicationUser m WHERE lower(m.lastName) LIKE %:searchTerm% " +
            "OR lower(m.firstName) LIKE %:searchTerm% OR lower(m.username) LIKE %:searchTerm%  ")
    Page<ApplicationUser> search(Pageable pageable, @Param("searchTerm") String searchTerm);
}
