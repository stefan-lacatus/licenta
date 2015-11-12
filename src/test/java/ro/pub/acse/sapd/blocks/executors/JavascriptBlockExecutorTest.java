package ro.pub.acse.sapd.blocks.executors;

import org.junit.Test;
import ro.pub.acse.sapd.blocks.BlockExecutionException;

import javax.script.ScriptException;

import static org.junit.Assert.*;

/**
 * Created by petrisor on 11/11/15.
 */
public class JavascriptBlockExecutorTest {
    @Test
    public void testJsContext() throws ScriptException, BlockExecutionException {
        JavascriptBlockExecutor test = new JavascriptBlockExecutor();
        test.processData("function parseInput(data) {return new FloatDataPoint(1.0);}",null);
    }
}