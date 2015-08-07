package ro.pub.acse.sapd.input;

import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.data.DataPointImpl;
import ro.pub.acse.sapd.data.DataType;

/**
 * A block that just passes the data through
 */
public class InputStringProcessorBlock<T extends DataPoint<String>> implements InputPreprocessorBlock<T> {
    @Override
    public T processData(String data) {
        return new DataPoint<S>
    }
}
