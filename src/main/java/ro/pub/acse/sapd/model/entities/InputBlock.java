package ro.pub.acse.sapd.model.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Represents an input block that groups several channels
 */
@Entity
public class InputBlock implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String description;
    private List<InputChannel> channels;
    private ApplicationUser lastEditedBy;
    private boolean enabled;
    private Date lastEditedTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany
    @JoinColumn(name = "input_block_id")
    @OrderBy("id asc")
    public List<InputChannel> getChannels() {
        return channels;
    }

    public void setChannels(List<InputChannel> channels) {
        this.channels = channels;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public ApplicationUser getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(ApplicationUser lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getLastEditedTime() {
        return lastEditedTime;
    }

    public void setLastEditedTime(Date lastEditedTime) {
        this.lastEditedTime = lastEditedTime;
    }
}
