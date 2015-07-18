package ro.pub.acse.sapd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.pub.acse.sapd.model.entities.ApplicationUser;

/**
 * Used for getting users out of the database
 */
@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUserName(String userName);
}
