package ro.pub.acse.sapd.input.impl;

import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.data.impl.IntegerDataPoint;
import ro.pub.acse.sapd.input.InputParseException;
import ro.pub.acse.sapd.input.InputPreprocessorBlock;

/**
 * A block that just passes the data through
 */
public class InputIntegerProcessorBlock implements InputPreprocessorBlock<DataPoint<Integer>> {

    @Override
    public DataPoint<Integer> processData(String data) throws InputParseException {
        try {
            return new IntegerDataPoint(Integer.parseInt(data));
        } catch (NumberFormatException ex) {
            throw new InputParseException(ex);
        }
    }
}
