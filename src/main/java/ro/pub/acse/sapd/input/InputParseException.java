package ro.pub.acse.sapd.input;

/**
 * Created by petrisor on 8/9/15.
 */
public class InputParseException extends Exception {
    private static final long serialVersionUID = 7506649086375256321L;

    public InputParseException(Exception ex) {
        super(ex);
    }

    public InputParseException(NumberFormatException ex) {
        super("Failed to parse number", ex.getCause());
    }
}
