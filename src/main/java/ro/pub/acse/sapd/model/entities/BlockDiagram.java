package ro.pub.acse.sapd.model.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Represents an function block diagram
 */
@Entity
public class BlockDiagram extends ManagedEntity implements Serializable {
    private static final long serialVersionUID = -4876463382677721689L;

    private String code;
    private DataChannel channel;

    @ManyToOne(cascade = CascadeType.ALL)
    public DataChannel getChannel() {
        return channel;
    }

    public void setChannel(DataChannel channel) {
        this.channel = channel;
    }

    @Column(columnDefinition="text")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
