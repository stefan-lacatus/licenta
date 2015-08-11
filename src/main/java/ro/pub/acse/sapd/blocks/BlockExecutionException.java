package ro.pub.acse.sapd.blocks;

/**
 * Occurs when the execution of a block fails
 */
public class BlockExecutionException extends Exception {
    private static final long serialVersionUID = -7468891391166019368L;

    public BlockExecutionException(Exception ex) {
        super(ex);
    }
}
