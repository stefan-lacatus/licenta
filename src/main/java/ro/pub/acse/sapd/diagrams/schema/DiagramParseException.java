package ro.pub.acse.sapd.diagrams.schema;

/**
 * Occurs when the execution of a block fails
 */
public class DiagramParseException extends Exception {
    private static final long serialVersionUID = -3468880251166019368L;

    public DiagramParseException(Exception ex) {
        super(ex);
    }

    public DiagramParseException(String message) {
        super(message);
    }
}
