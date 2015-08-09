package ro.pub.acse.sapd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.pub.acse.sapd.model.entities.InputChannel;
import ro.pub.acse.sapd.repository.custom.InputChannelCustomRepository;

/**
 * Used for getting input channels from the database
 */
@Repository
public interface InputChannelRepository extends JpaRepository<InputChannel, Long>, InputChannelCustomRepository {
}