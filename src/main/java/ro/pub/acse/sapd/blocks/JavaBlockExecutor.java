package ro.pub.acse.sapd.blocks;

import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.input.InputParseException;

import java.util.List;

/**
 * Executes a java type block
 */
public class JavaBlockExecutor {
    private <T> T instantiate(final String className, final Class<T> type) {
        try {
            return type.cast(Class.forName(className).newInstance());
        } catch (final InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public <T> DataPoint processData(final String className, List<DataPoint<T>> data) throws InputParseException {
        return instantiate(className, ProcessorBlockFunction.class).processData(data);
    }
}
