package ro.pub.acse.sapd.blocks;

import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.input.InputParseException;

import java.util.List;

/**
 * A interface that takes a list of data points and preforms
 * a certain operation on them
 */
public interface ProcessorBlockFunction<T extends DataPoint> {
    T processData(List<DataPoint> data) throws InputParseException;
}
