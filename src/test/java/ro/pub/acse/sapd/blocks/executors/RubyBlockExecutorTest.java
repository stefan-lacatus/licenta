package ro.pub.acse.sapd.blocks.executors;

import org.junit.Test;
import ro.pub.acse.sapd.blocks.BlockExecutionException;

import javax.script.ScriptException;

/**
 * Created by petrisor on 11/11/15.
 */
public class RubyBlockExecutorTest {
    @Test
    public void testJsContext() throws ScriptException, BlockExecutionException {
        RubyBlockExecutor test = new RubyBlockExecutor();
        test.processData("def parseInput(data) \n" +
                "$log.warn('test')\n" +
                "FloatDataPoint.new(1.0);\n" +
                "end",null);
    }
}