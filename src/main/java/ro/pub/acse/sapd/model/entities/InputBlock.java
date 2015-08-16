package ro.pub.acse.sapd.model.entities;


import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import java.io.Serializable;
import java.util.List;

/**
 * Represents an input block that groups several channels
 */
@Entity
public class InputBlock extends ManagedEntity implements Serializable {
    private static final long serialVersionUID = 4434541342673637095L;

    private List<DataChannel> channels;

    @ManyToMany()
    @OrderBy("id asc")
    public List<DataChannel> getChannels() {
        return channels;
    }

    public void setChannels(List<DataChannel> channels) {
        this.channels = channels;
    }
}
