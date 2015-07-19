package ro.pub.acse.sapd.model.entities;

import ro.pub.acse.sapd.blocks.ProcessorBlockType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * A block that takes in input data and returns output data
 */
@Entity
public class ProcessorBlock implements Serializable {
    private static final long serialVersionUID = 7068416575004254332L;

    private long id;
    private String name;
    private String description;
    private String functionCode;
    private ProcessorBlockType blockType;
    private String code;
    private List<ApplicationTag> tags;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ProcessorBlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(ProcessorBlockType blockType) {
        this.blockType = blockType;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    @Column(unique = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @OneToMany
    public List<ApplicationTag> getTags() {
        return tags;
    }

    public void setTags(List<ApplicationTag> tags) {
        this.tags = tags;
    }
}
