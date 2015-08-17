package ro.pub.acse.sapd.diagrams.executor.graph;

/**
 * Occurs when the topological sorting fails (a cyclic dependency is detected)
 */
public class TopologicalSortException extends Exception {
    private static final long serialVersionUID = -3468880251166019368L;

    public TopologicalSortException(Exception ex) {
        super(ex);
    }

    public TopologicalSortException(String message) {
        super(message);
    }
}
