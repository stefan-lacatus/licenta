package ro.pub.acse.sapd.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.model.entities.DataChannel;
import ro.pub.acse.sapd.repository.custom.DataChannelCustomRepository;

import java.util.Date;
import java.util.List;

/**
 * Used for getting input channels from the database
 */
@Repository
public interface DataChannelRepository extends JpaRepository<DataChannel, Long>, DataChannelCustomRepository {
    @Query("SELECT p FROM DataPointEntity p WHERE p.channel = :channel and  p.timestamp between :startInterval and :endInterval")
    List<DataPoint> getDataOnChannel(@Param("channel") DataChannel channel,
                                     @Param("startInterval") Date startInterval,
                                     @Param("endInterval") Date endInterval);

    @Query(value = "Select p FROM DataPointEntity p WHERE p.channel = :channel order by p.timestamp DESC")
    List<DataPoint> getLastDataOnChannel(@Param("channel") DataChannel channel, Pageable pageable);
}