package ro.pub.acse.sapd.data;

/**
 * Data types that are accepted by the system. This is an enum in order to be easily
 */
public enum DataType {
    /**
     * Integer data type, from -2^63 to 2^63 -1, using {@link Long} as an internal representation
     */
    INTEGER(),
    /**
     * Floating point data type, 4 bytes of precision using {@link Float} as an internal representation
     */
    FLOAT(),
    /**
     * String data type, as a text, escaped according to RFC4627, using a {@link String} as internal representation
     * @see <a href="http://www.ietf.org/rfc/rfc4627.txt">RFC4627</a>
     */
    STRING()
}
