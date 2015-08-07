package ro.pub.acse.sapd.repository.custom;

import ro.pub.acse.sapd.data.DataPoint;

/**
 * Handles new data that is inbound into our system.
 */
public interface InputChannelCustomRepository {
    void addDataToTable(String channelName, Long inputBlockId, DataPoint data);
}
