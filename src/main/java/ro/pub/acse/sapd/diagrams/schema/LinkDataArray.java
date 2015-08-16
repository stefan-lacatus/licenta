
package ro.pub.acse.sapd.diagrams.schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "from",
        "to",
        "fromPort",
        "toPort"
})
public class LinkDataArray {

    @JsonProperty("from")
    private long from;
    @JsonProperty("to")
    private long to;
    @JsonProperty("fromPort")
    private String fromPort;
    @JsonProperty("toPort")
    private String toPort;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The from
     */
    @JsonProperty("from")
    public long getFrom() {
        return from;
    }

    /**
     * @param from The from
     */
    @JsonProperty("from")
    public void setFrom(long from) {
        this.from = from;
    }

    /**
     * @return The to
     */
    @JsonProperty("to")
    public long getTo() {
        return to;
    }

    /**
     * @param to The to
     */
    @JsonProperty("to")
    public void setTo(long to) {
        this.to = to;
    }

    /**
     * @return The fromPort
     */
    @JsonProperty("fromPort")
    public String getFromPort() {
        return fromPort;
    }

    /**
     * @param fromPort The fromPort
     */
    @JsonProperty("fromPort")
    public void setFromPort(String fromPort) {
        this.fromPort = fromPort;
    }

    /**
     * @return The toPort
     */
    @JsonProperty("toPort")
    public String getToPort() {
        return toPort;
    }

    /**
     * @param toPort The toPort
     */
    @JsonProperty("toPort")
    public void setToPort(String toPort) {
        this.toPort = toPort;
    }

}
