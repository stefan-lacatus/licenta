package ro.pub.acse.sapd.blocks.executors;

/**
 * Executes a javascript block using nashorn
 */
public class JavascriptBlockExecutor extends ScriptEngineBlockExecutor {

    public JavascriptBlockExecutor() {
        super("nashorn");
    }
}
