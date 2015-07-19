package ro.pub.acse.sapd.blocks;

/**
 * Type of the processor block
 */
public enum ProcessorBlockType {
    /**
     * This represents a java class that implements the ProcessorBlockFunction
     */
    JAVA,
    /**
     * A javascript function that respects the functional declaration in the ProcessorBlockFunction
     */
    JAVASCRIPT
}
