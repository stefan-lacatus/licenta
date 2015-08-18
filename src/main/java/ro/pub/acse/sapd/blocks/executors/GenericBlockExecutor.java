package ro.pub.acse.sapd.blocks.executors;

import ro.pub.acse.sapd.blocks.BlockExecutionException;
import ro.pub.acse.sapd.data.DataPoint;

import java.util.List;

/**
 * A executor that runs code of a block
 */
public interface GenericBlockExecutor {
    DataPoint processData(String script, List<DataPoint> data) throws BlockExecutionException;
}
