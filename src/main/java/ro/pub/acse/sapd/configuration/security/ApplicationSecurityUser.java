package ro.pub.acse.sapd.configuration.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ro.pub.acse.sapd.model.entities.ApplicationUser;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A manager that logs into the application
 */
public class ApplicationSecurityUser extends ApplicationUser implements UserDetails {
    public ApplicationSecurityUser(ApplicationUser user) {
        if (user != null) {
            this.setId(user.getId());
            this.setEmail(user.getEmail());
            this.setUserName(user.getUserName());
            this.setPassword(user.getPassword());
            this.setActive(user.isActive());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add((new SimpleGrantedAuthority("ADMIN")));
        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return super.isActive();
    }
}
