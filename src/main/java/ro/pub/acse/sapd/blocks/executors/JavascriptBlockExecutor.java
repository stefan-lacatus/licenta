package ro.pub.acse.sapd.blocks.executors;

import ro.pub.acse.sapd.blocks.BlockExecutionException;
import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.data.impl.StringDataPoint;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

/**
 * Executes a javascript block using nashorn
 */
public class JavascriptBlockExecutor implements GenericBlockExecutor {
    private final ScriptEngine engine;

    public JavascriptBlockExecutor() {
        engine = new ScriptEngineManager().getEngineByName("nashorn");
    }

    public <T> DataPoint processData(final String script, List<DataPoint<T>> data) throws BlockExecutionException {
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
