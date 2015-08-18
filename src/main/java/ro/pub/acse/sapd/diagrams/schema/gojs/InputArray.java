package ro.pub.acse.sapd.diagrams.schema.gojs;


import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "portColor",
        "portId"
})
public class InputArray {

    @JsonProperty("portColor")
    private String portColor;
    @JsonProperty("portId")
    private String portId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The portColor
     */
    @JsonProperty("portColor")
    public String getPortColor() {
        return portColor;
    }

    /**
     * @param portColor The portColor
     */
    @JsonProperty("portColor")
    public void setPortColor(String portColor) {
        this.portColor = portColor;
    }

    /**
     * @return The portId
     */
    @JsonProperty("portId")
    public String getPortId() {
        return portId;
    }

    /**
     * @param portId The portId
     */
    @JsonProperty("portId")
    public void setPortId(String portId) {
        this.portId = portId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
