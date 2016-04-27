package learn.springsecurity.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static util.Utils.assume;

/**
 * {@link Authentication} serves two purposes.
 * <p>
 * Before authentication, it is the token for an authentication request.
 * After the request has been processed by the
 * {@link AuthenticationManager#authenticate(Authentication)}, it is an authenticated principal.
 * <p>
 * {@link SecurityContext} defines the minimum security information associated with the current thread
 * of execution. It's a container holding Authentication.
 * <p>
 * {@link SecurityContextHolder} associates a given {@link SecurityContext} with the current execution thread.
 * <p>
 * SecurityContext is usually obtained via SecurityContextHolder.
 * <pre>SecurityContextHolder.getContext().getAuthentication(Authentication)</pre>
 * <p>
 * <img src="doc-files/Authentication_SecurityContext.png" width="700" height="550" />
 *
 */
public class O01_Authentication_and_SecurityContext {

    public static void main(String[] args) {

        // Authentication token gathered from user input
        //
        // Mostly this is done with the aid of Spring Security built-in components.
        // e.g. UsernamePasswordAuthenticationFilter
        String username = assume("gathered from user input");
        String password = assume("gathered from user input");

        Authentication token = new UsernamePasswordAuthenticationToken(username, password);

        // Obtain an Authentication representing the user on successful authentication
        Authentication authenticatedPrincipal =
                assume("AuthenticationManager successfully authenticated this user with the given token, " +
                        "and returned a principal representing this user");


        // again, setting authenticated Authentication to SecurityContext is usually done
        // by Spring Security built-in components. e.g. AuthenticationProcessingFilter.
        SecurityContextHolder.getContext().setAuthentication(authenticatedPrincipal);

    }

}
