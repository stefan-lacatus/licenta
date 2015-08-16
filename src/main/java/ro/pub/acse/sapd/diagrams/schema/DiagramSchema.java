package ro.pub.acse.sapd.diagrams.schema;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "class",
        "linkFromPortIdProperty",
        "linkToPortIdProperty",
        "nodeDataArray",
        "linkDataArray"
})
public class DiagramSchema {
    @JsonProperty("class")
    private String _class;
    @JsonProperty("linkFromPortIdProperty")
    private String linkFromPortIdProperty;
    @JsonProperty("linkToPortIdProperty")
    private String linkToPortIdProperty;
    @JsonProperty("nodeDataArray")
    @Valid
    private List<NodeDataArray> nodeDataArray = new ArrayList<NodeDataArray>();
    @JsonProperty("linkDataArray")
    @Valid
    private List<LinkDataArray> linkDataArray = new ArrayList<LinkDataArray>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The _class
     */
    @JsonProperty("class")
    public String getClass_() {
        return _class;
    }

    /**
     * @param _class The class
     */
    @JsonProperty("class")
    public void setClass_(String _class) {
        this._class = _class;
    }

    /**
     * @return The linkFromPortIdProperty
     */
    @JsonProperty("linkFromPortIdProperty")
    public String getLinkFromPortIdProperty() {
        return linkFromPortIdProperty;
    }

    /**
     * @param linkFromPortIdProperty The linkFromPortIdProperty
     */
    @JsonProperty("linkFromPortIdProperty")
    public void setLinkFromPortIdProperty(String linkFromPortIdProperty) {
        this.linkFromPortIdProperty = linkFromPortIdProperty;
    }

    /**
     * @return The linkToPortIdProperty
     */
    @JsonProperty("linkToPortIdProperty")
    public String getLinkToPortIdProperty() {
        return linkToPortIdProperty;
    }

    /**
     * @param linkToPortIdProperty The linkToPortIdProperty
     */
    @JsonProperty("linkToPortIdProperty")
    public void setLinkToPortIdProperty(String linkToPortIdProperty) {
        this.linkToPortIdProperty = linkToPortIdProperty;
    }

    /**
     * @return The nodeDataArray
     */
    @JsonProperty("nodeDataArray")
    public List<NodeDataArray> getNodeDataArray() {
        return nodeDataArray;
    }

    /**
     * @param nodeDataArray The nodeDataArray
     */
    @JsonProperty("nodeDataArray")
    public void setNodeDataArray(List<NodeDataArray> nodeDataArray) {
        this.nodeDataArray = nodeDataArray;
    }

    /**
     * @return The linkDataArray
     */
    @JsonProperty("linkDataArray")
    public List<LinkDataArray> getLinkDataArray() {
        return linkDataArray;
    }

    /**
     * @param linkDataArray The linkDataArray
     */
    @JsonProperty("linkDataArray")
    public void setLinkDataArray(List<LinkDataArray> linkDataArray) {
        this.linkDataArray = linkDataArray;
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
