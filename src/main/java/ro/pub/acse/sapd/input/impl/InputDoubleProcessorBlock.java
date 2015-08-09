package ro.pub.acse.sapd.input.impl;

import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.data.impl.FloatDataPoint;
import ro.pub.acse.sapd.input.InputParseException;
import ro.pub.acse.sapd.input.InputPreprocessorBlock;

/**
 * A block that just passes the data through
 */
public class InputDoubleProcessorBlock implements InputPreprocessorBlock<DataPoint<Double>> {

    @Override
    public DataPoint<Double> processData(String data) throws InputParseException {
        try {
            return new FloatDataPoint(Double.parseDouble(data));
        } catch (NumberFormatException ex) {
            throw new InputParseException(ex);
        }
    }
}
