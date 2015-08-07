package ro.pub.acse.sapd.data;

import java.time.Instant;

/**
 * This represents one point in a time-series
 */
public interface DataPoint<E> {
    E getValue();
    Instant getTimeStamp();
}
