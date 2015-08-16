package ro.pub.acse.sapd.diagrams.schema;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "category",
        "text",
        "key",
        "loc",
        "blockId",
        "internalType",
        "processorId",
        "processorName",
        "comments"
})
public class NodeDataArray {

    @JsonProperty("category")
    private String category;
    @JsonProperty("text")
    private String text;
    @JsonProperty("key")
    private long key;
    @JsonProperty("loc")
    private String loc;
    @JsonProperty("blockId")
    private long blockId;
    @JsonProperty("internalType")
    private String internalType;
    @JsonProperty("processorId")
    private long processorId;
    @JsonProperty("processorName")
    private String processorName;
    @JsonProperty("comments")
    private String comments;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     * @return The processorId
     */
    @JsonProperty("processorId")
    public long getProcessorId() {
        return processorId;
    }

    /**
     * @param processorId The processorId
     */
    @JsonProperty("processorId")
    public void setProcessorId(long processorId) {
        this.processorId = processorId;
    }

    /**
     * @return The processorName
     */
    @JsonProperty("processorName")
    public String getProcessorName() {
        return processorName;
    }

    /**
     * @param processorName The processorName
     */
    @JsonProperty("processorName")
    public void setProcessorName(String processorName) {
        this.processorName = processorName;
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
