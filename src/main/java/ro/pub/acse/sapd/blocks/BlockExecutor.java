package ro.pub.acse.sapd.blocks;

import org.slf4j.Logger;
import ro.pub.acse.sapd.blocks.executors.GenericBlockExecutor;
import ro.pub.acse.sapd.blocks.executors.JavaBlockExecutor;
import ro.pub.acse.sapd.blocks.executors.JavascriptBlockExecutor;
import ro.pub.acse.sapd.blocks.executors.RubyBlockExecutor;
import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.logging.Loggable;
import ro.pub.acse.sapd.model.entities.ProcessorBlock;

import javax.script.ScriptException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Executes blocks
 */
public class BlockExecutor {
    @Loggable
    private Logger log;

    private Map<ProcessorBlockType, GenericBlockExecutor> executors = new HashMap<>();

    public BlockExecutor() {
        executors.put(ProcessorBlockType.JAVA, new JavaBlockExecutor());
        try {
            executors.put(ProcessorBlockType.JAVASCRIPT, new JavascriptBlockExecutor());
            executors.put(ProcessorBlockType.RUBY, new RubyBlockExecutor());
        } catch (ScriptException e) {
            log.warn("Failed to initialize the script manager functions", e);
        }
    }

    public DataPoint execute(ProcessorBlock block, List<DataPoint> points) throws BlockExecutionException {
        if(executors.containsKey(block.getBlockType())) {
            return executors.get(block.getBlockType()).processData(block.getFunctionCode(), points);
        }  else {
            return points.get(0);
        }
    }
}
