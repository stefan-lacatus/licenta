package ro.pub.acse.sapd.diagrams.schema.gojs;


import com.fasterxml.jackson.annotation.*;
import ro.pub.acse.sapd.diagrams.schema.gojs.InputArray;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "key",
        "category",
        "text",
        "loc",
        "blockId",
        "internalType",
        "inputArray",
        "comments"
})
public class NodeDataArray {

    @JsonProperty("key")
    private long key;
    @JsonProperty("category")
    private String category;
    @JsonProperty("text")
    private String text;
    @JsonProperty("loc")
    private String loc;
    @JsonProperty("blockId")
    private long blockId;
    @JsonProperty("internalType")
    private String internalType;
    @JsonProperty("inputArray")
    @Valid
    private List<InputArray> inputArray = new ArrayList<InputArray>();
    @JsonProperty("comments")
    private String comments;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The key
     */
    @JsonProperty("key")
    public long getKey() {
        return key;
    }

    /**
     * @param key The key
     */
    @JsonProperty("key")
    public void setKey(long key) {
        this.key = key;
    }

    /**
     * @return The category
     */
    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    /**
     * @param category The category
     */
    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return The text
     */
    @JsonProperty("text")
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return The loc
     */
    @JsonProperty("loc")
    public String getLoc() {
        return loc;
    }

    /**
     * @param loc The loc
     */
    @JsonProperty("loc")
    public void setLoc(String loc) {
        this.loc = loc;
    }

    /**
     * @return The blockId
     */
    @JsonProperty("blockId")
    public long getBlockId() {
        return blockId;
    }

    /**
     * @param blockId The blockId
     */
    @JsonProperty("blockId")
    public void setBlockId(long blockId) {
        this.blockId = blockId;
    }

    /**
     * @return The internalType
     */
    @JsonProperty("internalType")
    public String getInternalType() {
        return internalType;
    }

    /**
     * @param internalType The internalType
     */
    @JsonProperty("internalType")
    public void setInternalType(String internalType) {
        this.internalType = internalType;
    }

    /**
     * @return The inputArray
     */
    @JsonProperty("inputArray")
    public List<InputArray> getInputArray() {
        return inputArray;
    }

    /**
     * @param inputArray The inputArray
     */
    @JsonProperty("inputArray")
    public void setInputArray(List<InputArray> inputArray) {
        this.inputArray = inputArray;
    }

    /**
     * @return The comments
     */
    @JsonProperty("comments")
    public String getComments() {
        return comments;
    }

    /**
     * @param comments The comments
     */
    @JsonProperty("comments")
    public void setComments(String comments) {
        this.comments = comments;
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
