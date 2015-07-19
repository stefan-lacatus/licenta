package ro.pub.acse.sapd.model.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Represents a user in the application
 */
@Entity
public class ApplicationUser implements Serializable {
    private static final long serialVersionUID = -5406081783045553462L;

    private String userName;
    private long id;
    private String password;
    private String email;
    private boolean active;

    @Column(unique = true)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
