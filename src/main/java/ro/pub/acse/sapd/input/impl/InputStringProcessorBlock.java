package ro.pub.acse.sapd.input.impl;

import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.data.impl.StringDataPoint;
import ro.pub.acse.sapd.input.InputParseException;
import ro.pub.acse.sapd.input.InputPreprocessorBlock;

import java.util.List;

/**
 * A block that just passes the data through
 */
public class InputStringProcessorBlock implements InputPreprocessorBlock<DataPoint<String>> {

    @Override
    public DataPoint<String> processData(String data) {
        return new StringDataPoint(data);
    }


    @Override
    public DataPoint processData(List<DataPoint> data) throws InputParseException {
        return processData((String) data.get(0).getValue());
    }
}
