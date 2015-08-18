package ro.pub.acse.sapd.data.impl;

import ro.pub.acse.sapd.data.DataPoint;

import java.util.Date;

/**
 * Created by placatus on 07.08.2015.
 */
public class ObjectDataPoint implements DataPoint<Object> {
    private String value;
    private Date instant;

    public ObjectDataPoint(String value) {
        this.value = value;
        instant = new Date();
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Date getTimeStamp() {
        return instant;
    }
}
