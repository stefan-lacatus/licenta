package ro.pub.acse.sapd.blocks.executors;

import ro.pub.acse.sapd.blocks.BlockExecutionException;
import ro.pub.acse.sapd.blocks.ProcessorBlockFunction;
import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.input.InputParseException;

import java.util.List;

/**
 * Executes a java type block
 */
public class JavaBlockExecutor implements GenericBlockExecutor {
    private <T> T instantiate(final String className, final Class<T> type) {
        try {
            return type.cast(Class.forName(className).newInstance());
        } catch (final InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public DataPoint processData(final String className, List<DataPoint> data) throws BlockExecutionException {
        try {
            return instantiate(className, ProcessorBlockFunction.class).processData(data);
        } catch (InputParseException ex) {
            throw new BlockExecutionException(ex);
        }
    }
}
