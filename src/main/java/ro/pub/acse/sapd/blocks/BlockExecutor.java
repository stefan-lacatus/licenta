package ro.pub.acse.sapd.blocks;

import org.slf4j.Logger;
import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.input.InputParseException;
import ro.pub.acse.sapd.logging.Loggable;
import ro.pub.acse.sapd.model.entities.ProcessorBlock;

import java.util.List;

/**
 * Executes blocks
 */
public class BlockExecutor {
    private final JavaBlockExecutor javaBlockExecutor;
    @Loggable
    private Logger log;

    public BlockExecutor() {
        javaBlockExecutor = new JavaBlockExecutor();
    }

    public <T> DataPoint execute(ProcessorBlock block, List<DataPoint<T>> points) throws InputParseException {
        if (block.getBlockType().equals(ProcessorBlockType.JAVA)) {
            try {
                return javaBlockExecutor.processData(block.getFunctionCode(), points);
            } catch (IllegalStateException ex) {
                log.error("Failed to process data on block " + block.getName(), ex);
                throw ex;
            }
        } else {
            return null;
        }
    }
}
