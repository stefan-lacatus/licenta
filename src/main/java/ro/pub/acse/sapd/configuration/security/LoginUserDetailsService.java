package ro.pub.acse.sapd.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ro.pub.acse.sapd.model.entities.ApplicationUser;
import ro.pub.acse.sapd.repository.ApplicationUserRepository;

@Component
public class LoginUserDetailsService implements UserDetailsService {

    @Autowired
    private ApplicationUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findByUserName(userName);
        if (user != null) {
            return new ApplicationSecurityUser(user);

        } else {
            throw new UsernameNotFoundException("UserName " + userName + " not found");
        }
    }
}