package ro.pub.acse.sapd.diagrams.executor;

/**
 * Occurs when the execution of a block fails
 */
public class DiagramExecutionException extends Exception {
    private static final long serialVersionUID = -3468880251166019368L;

    public DiagramExecutionException(Exception ex) {
        super(ex);
    }

    public DiagramExecutionException(String message) {
        super(message);
    }
}
