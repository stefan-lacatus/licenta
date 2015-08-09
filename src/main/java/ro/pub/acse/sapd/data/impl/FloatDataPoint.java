package ro.pub.acse.sapd.data.impl;

import ro.pub.acse.sapd.data.DataPoint;

import java.time.Instant;

/**
 * Created by placatus on 07.08.2015.
 */
public class FloatDataPoint implements DataPoint<Double> {
    private final Instant instant;
    private double value;

    public FloatDataPoint(double value) {
        this.value = value;
        instant = Instant.now();
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public Instant getTimeStamp() {
        return instant;
    }
}
