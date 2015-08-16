package ro.pub.acse.sapd.model.entities;

import ro.pub.acse.sapd.blocks.ProcessorBlockType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A block that takes in input data and returns output data
 */
@Entity
public class ProcessorBlock extends ManagedEntity implements Serializable {
    private static final long serialVersionUID = 7068416575004254332L;

    private String functionCode;
    private ProcessorBlockType blockType;

    @NotNull
    @Enumerated(EnumType.STRING)
    public ProcessorBlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(ProcessorBlockType blockType) {
        this.blockType = blockType;
    }

    @Column(columnDefinition="text")
    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }
}
