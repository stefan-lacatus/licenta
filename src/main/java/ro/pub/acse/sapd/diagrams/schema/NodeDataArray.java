
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
        "category",
        "text",
        "key"
})
public class NodeDataArray {

    @JsonProperty("category")
    private String category;
    @JsonProperty("text")
    private String text;
    @JsonProperty("key")
    private long key;
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
}
