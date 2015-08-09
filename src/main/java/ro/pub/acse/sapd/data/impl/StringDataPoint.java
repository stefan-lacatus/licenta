package ro.pub.acse.sapd.data.impl;

import ro.pub.acse.sapd.data.DataPoint;

import java.time.Instant;

/**
 * Created by placatus on 07.08.2015.
 */
public class StringDataPoint implements DataPoint<String> {
    private String value;
    private Instant instant;

    public StringDataPoint(String value) {
        this.value = value;
        instant = Instant.now();
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Instant getTimeStamp() {
        return instant;
    }
}
