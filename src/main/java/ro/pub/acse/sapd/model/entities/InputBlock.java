package ro.pub.acse.sapd.model.entities;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.io.Serializable;
import java.util.List;

/**
 * Represents an input block that groups several channels
 */
@Entity
public class InputBlock extends ManagedEntity implements Serializable {
    private static final long serialVersionUID = 4434541342673637095L;

    private List<InputChannel> channels;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "input_block_id")
    @OrderBy("id asc")
    public List<InputChannel> getChannels() {
        return channels;
    }

    public void setChannels(List<InputChannel> channels) {
        if (this.channels == null) {
            this.channels = channels;
        } else {
            if(channels != this.channels) {
                this.channels.clear();
                if (channels != null) {
                    this.channels.addAll(channels);
                }
            }
        }
    }
}
