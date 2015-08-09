package ro.pub.acse.sapd.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(2)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginUserDetailsService loginUserDetailsService;
    @Autowired
    private TokenBasedRememberMeService tokenBasedRememberMeService;
    @Autowired
    private RememberMeAuthenticationProvider rememberMeAuthenticationProvider;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/management/**").hasAuthority("ADMIN")
                // .antMatchers("/reports/**").hasAuthority(LoginUserAuthorityType.MANAGER.getName())
                .anyRequest().authenticated();

        http.formLogin().failureUrl("/login?error")
                .defaultSuccessUrl("/home")
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
                .and().rememberMe().rememberMeServices(tokenBasedRememberMeService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
        auth.authenticationProvider(rememberMeAuthenticationProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}