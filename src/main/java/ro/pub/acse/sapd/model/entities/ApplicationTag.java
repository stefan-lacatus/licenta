package ro.pub.acse.sapd.model.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Tags used to separate entities in the application
 */
@Entity
public class ApplicationTag implements Serializable {
    private static final long serialVersionUID = -3768915208729921224L;

    private long id;
    private String name;

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

    @Column(unique = true)
    public void setId(long id) {
        this.id = id;
    }
}
