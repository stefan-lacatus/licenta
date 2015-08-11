package ro.pub.acse.sapd.input;

import ro.pub.acse.sapd.blocks.ProcessorBlockFunction;
import ro.pub.acse.sapd.data.DataPoint;

/**
 * An input pre-processor block gets raw data-points as String and returns a DataPoint representation of it
 */
public interface InputPreprocessorBlock<T extends DataPoint> extends ProcessorBlockFunction<DataPoint> {
    T processData(String data) throws InputParseException;
}
