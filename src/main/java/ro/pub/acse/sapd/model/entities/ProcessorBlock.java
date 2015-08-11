package ro.pub.acse.sapd.model.entities;

import ro.pub.acse.sapd.blocks.ProcessorBlockType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
    private Set<ApplicationTag> tags;
    private ApplicationUser lastEditedBy;
    private Date lastEditedTime;
    private boolean active;

    @NotNull
    @Enumerated(EnumType.STRING)
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(unique = true)
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

    @ManyToMany(cascade = CascadeType.ALL)
    public Set<ApplicationTag> getTags() {
        return tags;
    }

    public void setTags(Set<ApplicationTag> tags) {
        this.tags = tags;
    }

    @ManyToOne
    public ApplicationUser getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(ApplicationUser lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Date getLastEditedTime() {
        return lastEditedTime;
    }

    public void setLastEditedTime(Date lastEditedTime) {
        this.lastEditedTime = lastEditedTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
