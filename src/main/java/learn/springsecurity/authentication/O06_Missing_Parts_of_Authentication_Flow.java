package learn.springsecurity.authentication;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Scanner;

/**
 * The Authentication flow is not complete solely with {@link AuthenticationManager}, its
 * related {@link AuthenticationProvider} and {@link UserDetailsService} configured.
 * <p>
 * What is missing is collecting request token and triggering authentication.
 * Here is the outline of complete authentication flow:
 * <ol>
 *     <li>Direct users to do authentication when they need to e.g. accessing protected resources</li>
 *     <li>Collect user's authentication information (username/password)</li>
 *     <li>Trigger authenticating request token by invoking {@link AuthenticationManager#authenticate(Authentication)}</li>
 *     <li>And one last thing that is specific to Spring Security is setting authenticated {@link Authentication} to {@link SecurityContext}</li>
 * </ol>
 * <p>
 * Usually a security framework would provide a convenience means or components to cover these.
 */
public class O06_Missing_Parts_of_Authentication_Flow {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:spring/simple-auth-runner.xml");

        AuthenticationRunner authRunner =
                applicationContext.getBean(AuthenticationRunner.class);

        authRunner.doAuth();

        System.out.println("User authenticated: " +
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated());

    }


    ///////////////////////////////////////////////////////////////////////////


    public static class AuthenticationRunner {
        private AuthenticationManager authenticationManager;

        public AuthenticationRunner(
                AuthenticationManager authenticationManager) {
            this.authenticationManager = authenticationManager;
        }

        public void doAuth() {
            // collect request token
            Authentication token = collectRequestToken();

            // trigger authentication
            Authentication authenticated = authenticate(token);

            // place Authentication in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authenticated);
        }

        private Authentication collectRequestToken() {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            return new UsernamePasswordAuthenticationToken(username, password);

        }

        private Authentication authenticate(Authentication token) {
            return this.authenticationManager.authenticate(token);
        }
    }

}
