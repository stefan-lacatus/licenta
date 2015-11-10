package ro.pub.acse.sapd.blocks.executors;

/**
 * Executes a ruby block using jruby
 */
public class RubyBlockExecutor extends ScriptEngineBlockExecutor {

    public RubyBlockExecutor() {
       super("jruby");
    }
}
