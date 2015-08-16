package ro.pub.acse.sapd.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Represents an function block diagram
 */
@Entity
public class BlockDiagram extends ManagedEntity implements Serializable {
    private static final long serialVersionUID = -4876463382677721689L;

    private String code;

    @Column(columnDefinition="text")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
