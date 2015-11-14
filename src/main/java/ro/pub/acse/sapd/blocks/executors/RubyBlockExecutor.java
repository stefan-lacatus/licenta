package ro.pub.acse.sapd.blocks.executors;

import javax.script.ScriptException;

/**
 * Executes a ruby block using jruby
 */
public class RubyBlockExecutor extends ScriptEngineBlockExecutor {

    public RubyBlockExecutor() throws ScriptException {
        super("jruby");
        engine.eval("require 'java'\n module M\n" +
                "  include_package \"ro.pub.acse.sapd.data.impl\"\n" +
                "end\n" +
                "\n" +
                "class Object\n" +
                "  class << self\n" +
                "    alias :const_missing_old :const_missing\n" +
                "    def const_missing c\n" +
                "      M.const_get c\n" +
                "    end\n" +
                "  end\n" +
                "end\n");
    }
}
