package ro.pub.acse.sapd.blocks.executors;

import javax.script.ScriptException;

/**
 * Executes a ruby block using jruby
 */
public class RubyBlockExecutor extends ScriptEngineBlockExecutor {

    public RubyBlockExecutor() throws ScriptException {
        super("jruby");
        engine.eval("require 'java'\n include_package 'ro.pub.acse.sapd.data.impl'");
    }
}
