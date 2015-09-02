package ro.pub.acse.sapd.configuration.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class TokenBasedRememberMeService extends TokenBasedRememberMeServices {

    private final String HEADER_SECURITY_TOKEN = "X-AUTH-TOKEN";

    public TokenBasedRememberMeService(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }

    /**
     * Locates the Spring Security remember me token in the request and returns its value.
     *
     * @param request the submitted request which is to be authenticated
     * @return the value of the request header (which was originally provided by the cookie - API expects it in header)
     */
    @Override
    protected String extractRememberMeCookie(HttpServletRequest request) {
        String token = request.getHeader(HEADER_SECURITY_TOKEN);
        if ((token == null) || (token.length() == 0)) {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals("at")) {
                        return cookie.getValue();
                    }
                }
                return null;
            }
        }
        return token;
    }
}
