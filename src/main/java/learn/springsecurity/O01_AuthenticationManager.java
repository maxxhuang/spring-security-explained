package learn.springsecurity;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static util.Utils.assume;

/**
 * The central component for deciding whether a request authentication token is valid or not is
 * {@link AuthenticationManager}.
 * <p>
 * AuthenticationManager defines only one method: {@link AuthenticationManager#authenticate(Authentication)}
 * that takes {@link Authentication} as request authentication token and return another authenticated
 * Authentication populated with name, expiration, disability and granted authorities.
 * <p>
 * As you can see, Authentication serves as 2 roles in Spring Security:
 *  1. before authentication, as request token
 *  2. after authentication, as authenticated principal loaded with granted authorities
 *  ({@link Authentication#getAuthorities()}).
 * <p>
 * Authority usually bears the information of user's granted permissions. However this is not mandatory.
 * The implementations of AuthenticationManager could choose not to populate {@link Authentication#getAuthorities()}.
 * The information of granted permission can be extracted from other source such as {@link Authentication#getDetails()}.
 * <p>
 * An authentication flow can be roughly divided into 3 steps as shown in the following pseudo code.
 * <p>
 * <img src="doc-files/AuthenticationManager.png" />
 */
public class O01_AuthenticationManager {

    public static void main(String[] args) {
        // step1: gather request token from user
        Authentication token = assume("authentication token from user input");

        // locate configured authenticationManager. This is taken care of by Spring container
        AuthenticationManager authenticationManager = assume("A configured Spring bean");

        // step3: perform authentication
        Authentication userPrincipal = authenticationManager.authenticate(token);

        // step4: put populated Authentication in SecurityContext for later use (authorization)
        SecurityContextHolder.getContext().setAuthentication(userPrincipal);

    }

}
