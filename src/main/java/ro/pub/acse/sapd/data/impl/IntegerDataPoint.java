package ro.pub.acse.sapd.data.impl;

import ro.pub.acse.sapd.data.DataPoint;

import java.time.Instant;

/**
 * Created by placatus on 07.08.2015.
 */
public class IntegerDataPoint implements DataPoint<Integer> {
    private final Instant instant;
    private int value;

    public IntegerDataPoint(int value) {
        this.value = value;
        instant = Instant.now();
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public Instant getTimeStamp() {
        return instant;
    }
}
