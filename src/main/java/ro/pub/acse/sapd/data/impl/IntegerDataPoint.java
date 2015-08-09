package ro.pub.acse.sapd.data.impl;

import ro.pub.acse.sapd.data.DataPoint;

import java.util.Date;

/**
 * Created by placatus on 07.08.2015.
 */
public class IntegerDataPoint implements DataPoint<Integer> {
    private final Date instant;
    private int value;

    public IntegerDataPoint(int value) {
        this.value = value;
        instant = new Date();
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public Date getTimeStamp() {
        return instant;
    }
}
