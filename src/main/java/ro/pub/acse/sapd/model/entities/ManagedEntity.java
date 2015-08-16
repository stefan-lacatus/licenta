package ro.pub.acse.sapd.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * A generic entity that has some fields common to all the entities that are managed within the application
 */
@MappedSuperclass
public class ManagedEntity {
    private long id;
    private String name;
    private String description;
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

    @Column(columnDefinition = "text")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    @JsonIgnore
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
