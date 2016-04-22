package Chapter01_Architecture;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static util.Utils.assume;

/**
 * <h2>Authentication</h2>
 * Before authentication, it is the token for an authentication request.
 * After the request has been processed by the
 * {@link AuthenticationManager#authenticate(Authentication)},
 * it is an authenticated principal.
 * <p>
 * <h2>SecurityContext</h2>
 * Interface defining the minimum security information associated with the current thread
 * of execution.
 * <p>
 * <h2>SecurityContextHolder</h2>
 * Associates a given {@link SecurityContext} with the current execution thread.
 * <p>
 * <img src="doc-files/Authentication_SecurityContext.png" width="700" height="550" />
 *
 */
public class Authentication_and_SecurityContext {

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
