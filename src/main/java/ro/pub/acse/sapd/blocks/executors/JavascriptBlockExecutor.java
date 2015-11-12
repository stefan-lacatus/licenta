package ro.pub.acse.sapd.blocks.executors;

import javax.script.ScriptException;

/**
 * Executes a javascript block using nashorn
 */
public class JavascriptBlockExecutor extends ScriptEngineBlockExecutor {

    public JavascriptBlockExecutor() throws ScriptException {
        super("js");
        engine.eval("if (typeof importPackage != 'function') {\n" +
                "   load('nashorn:mozilla_compat.js');\n" +
                "} importPackage('ro.pub.acse.sapd.data.impl')");
    }
}
