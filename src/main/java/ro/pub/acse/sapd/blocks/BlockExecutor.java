package ro.pub.acse.sapd.blocks;

import org.slf4j.Logger;
import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.input.InputParseException;
import ro.pub.acse.sapd.logging.Loggable;
import ro.pub.acse.sapd.model.entities.ProcessorBlock;

import javax.script.ScriptException;
import java.util.List;

/**
 * Executes blocks
 */
public class BlockExecutor {
    private final JavaBlockExecutor javaBlockExecutor;
    private final JavascriptBlockExecutor javascriptBlockExecutor;
    @Loggable
    private Logger log;

    public BlockExecutor() {
        javaBlockExecutor = new JavaBlockExecutor();
        javascriptBlockExecutor = new JavascriptBlockExecutor();
    }

    public <T> DataPoint execute(ProcessorBlock block, List<DataPoint<T>> points) throws BlockExecutionException {
        if (block.getBlockType().equals(ProcessorBlockType.JAVA)) {
            try {
                return javaBlockExecutor.processData(block.getFunctionCode(), points);
            } catch (IllegalStateException | InputParseException ex) {
                log.error("Failed to process data on block " + block.getName(), ex);
                throw new BlockExecutionException(ex);
            }
        } else if (block.getBlockType().equals(ProcessorBlockType.JAVASCRIPT)) {
            try {
                return javascriptBlockExecutor.processData(block.getFunctionCode(), points);
            } catch (ScriptException | NoSuchMethodException ex) {
                log.error("Failed to process data on block " + block.getName(), ex);
                throw new BlockExecutionException(ex);
            }
        } else {
            return null;
        }
    }
}
