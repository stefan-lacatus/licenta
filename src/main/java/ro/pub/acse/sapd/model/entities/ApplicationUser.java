package ro.pub.acse.sapd.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Represents a user in the application
 */
@Entity
public class ApplicationUser extends ManagedEntity implements Serializable {
    private static final long serialVersionUID = -5406081783045553462L;

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    @Column(unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
