package ro.pub.acse.sapd.blocks;

/**
 * Type of the processor block
 */
public enum ProcessorBlockType {
    /**
     * This represents a java class that implements the ProcessorBlockFunction
     */
    JAVA("Java"),
    /**
     * A javascript function that respects the functional declaration in the ProcessorBlockFunction
     */
    JAVASCRIPT("Javascript"),
    /**
     * A ruby function that respects the functional declaration in the ProcessorBlockFunction
     */
    RUBY("Ruby");

    private final String value;

    ProcessorBlockType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
