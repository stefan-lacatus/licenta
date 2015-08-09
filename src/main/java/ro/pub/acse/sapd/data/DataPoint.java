package ro.pub.acse.sapd.data;

import java.util.Date;

/**
 * This represents one point in a time-series
 */
public interface DataPoint<E> {
    E getValue();
    Date getTimeStamp();
}
