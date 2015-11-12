package ro.pub.acse.sapd.blocks.executors;

import org.slf4j.LoggerFactory;
import ro.pub.acse.sapd.SapdApplication;
import ro.pub.acse.sapd.blocks.BlockExecutionException;
import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.data.impl.StringDataPoint;

import javax.script.*;
import java.util.List;

/**
 * Executes a block using the script engine mechanism defined in java
 */
public class ScriptEngineBlockExecutor implements GenericBlockExecutor {
    protected final ScriptEngine engine;

    public ScriptEngineBlockExecutor(String engineName) {
        engine = new ScriptEngineManager().getEngineByName(engineName);
        // add some loggers to the engine
        engine.put("log", LoggerFactory.getLogger(SapdApplication.EVENT_LOGGER));
    }

    @Override
    public DataPoint processData(final String script, List<DataPoint> data) throws BlockExecutionException {
        try {
            engine.eval(script);
            Invocable invocable = (Invocable) engine;
            Object parseInput = invocable.invokeFunction("parseInput", data);
            if (parseInput instanceof DataPoint) {
                return (DataPoint) parseInput;
            } else {
                return new StringDataPoint(parseInput.toString());
            }
        } catch (ScriptException | NoSuchMethodException ex) {
            throw new BlockExecutionException(ex);
        }
    }
}
