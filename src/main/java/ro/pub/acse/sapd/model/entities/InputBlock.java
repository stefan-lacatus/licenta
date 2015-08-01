package ro.pub.acse.sapd.model.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Represents an input block that groups several channels
 */
@Entity
public class InputBlock implements Serializable {
    private static final long serialVersionUID = 4434541342673637095L;

    private long id;
    private String name;
    private String description;
    private List<InputChannel> channels;
    private ApplicationUser lastEditedBy;
    private boolean active;
    private Date lastEditedTime;
    private Set<ApplicationTag> tags;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @ManyToOne
    public ApplicationUser getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(ApplicationUser lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getLastEditedTime() {
        return lastEditedTime;
    }

    public void setLastEditedTime(Date lastEditedTime) {
        this.lastEditedTime = lastEditedTime;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    public Set<ApplicationTag> getTags() {
        return tags;
    }

    public void setTags(Set<ApplicationTag> tags) {
        this.tags = tags;
    }
}
