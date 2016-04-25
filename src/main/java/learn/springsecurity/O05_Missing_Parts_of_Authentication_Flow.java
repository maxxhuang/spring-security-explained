package learn.springsecurity;

import org.springframework.context.*;
import org.springframework.context.support.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.*;

/**
 * The Authentication flow is not complete solely with {@link AuthenticationManager} and all its
 * related {@link AuthenticationProvider} and {@link UserDetailsService} all set and configured.
 * <p>
 * What is missing is collecting request token and triggering authentication. And one last thing
 * that is specific to Spring Security is setting authenticated {@link Authentication} to
 * {@link SecurityContext}.
 * <p>
 * Usually a security framework would provide a convenience means or components to cover these.
 */
public class O05_Missing_Parts_of_Authentication_Flow {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:spring/simple-auth-runner.xml");

        ComponentToGatherRequestTokenAndTriggerAuthentication authRunner =
                applicationContext.getBean(ComponentToGatherRequestTokenAndTriggerAuthentication.class);

        authRunner.doAuth();

        System.out.println("User authenticated: " +
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated());

    }


    ///////////////////////////////////////////////////////////////////////////


    public static class ComponentToGatherRequestTokenAndTriggerAuthentication {
        private AuthenticationManager authenticationManager;

        public ComponentToGatherRequestTokenAndTriggerAuthentication(
                AuthenticationManager authenticationManager) {
            this.authenticationManager = authenticationManager;
        }

        public void doAuth() {
            // collect request token
            Authentication token = gatherRequestToken();

            // trigger authentication
            Authentication authenticated = authenticate(token);

            // place Authentication in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authenticated);
        }

        private Authentication gatherRequestToken() {
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
