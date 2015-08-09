package ro.pub.acse.sapd.data.impl;

import ro.pub.acse.sapd.data.DataPoint;

import java.util.Date;

/**
 * Created by placatus on 07.08.2015.
 */
public class FloatDataPoint implements DataPoint<Double> {
    private final Date instant;
    private double value;

    public FloatDataPoint(double value) {
        this.value = value;
        instant = new Date();
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public Date getTimeStamp() {
        return instant;
    }
}
