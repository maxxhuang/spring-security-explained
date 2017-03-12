package learn.springsecurity.authentication;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * {@link DaoAuthenticationProvider} defines a specific authentication procedure.
 * It delegates the data access of {@link UserDetails} to {@link UserDetailsService}
 * and password encryption / comparison to {@link PasswordEncoder}.
 * <p>
 * The following steps outline how DaoAuthenticationProvider performs authentication.
 * <ol>
 *     <li>
 *         Use configured UserDetailsService to fetch UserDetails by user name
 *         given from request token (Authentication)
 *     </li>
 *     <li>
 *         Use configured PasswordEncoder to validate, encrypt and compare password
 *     </li>
 * </ol>
 * Note: We omit some details such as user caching, UserDetails pre-check/post-check.
 * These are left as an exercise.
 * Take a look at {@link AbstractUserDetailsAuthenticationProvider#authenticate(Authentication)}
 * to explore the complete authentication procedure.
 * <p>
 * Several components need to be configured and wired together for DaoAuthenticationProvider
 * to come into play.
 * <p>
 * Spring Security manages to mitigate the configuration pain like this by way of "Spring Namespace Configuration"
 * or "Java Configuration"
 * <p>
 * The classes highlighted in green in the class diagram denote the components that are created via the following
 * Spring configuration:
 * <pre>
 *     &lt;authentication-manager id="authenticationManager"&gt;
 *         &lt;authentication-provider&gt;
 *             &lt;user-service&gt;
 *                 &lt;user name="max" password="rocks" authorities="ROLE_ADMIN" /&gt;
 *                 &lt;user name="rock" password="solid" authorities="ROLE_ADMIN" /&gt;
 *             &lt;/user-service&gt;
 *         &lt;/authentication-provider&gt;
 *     &lt;/authentication-manager&gt;
 * </pre>
 *
 * <img src="doc-files/DaoAuthenticationProvider_and_UserDetailsService_impl.png" width="800" height="800" alt="DaoAuthenticationProvider Class Diagram">
 */
public class O05_DaoAuthenticationProvider {

    public static void main(String[] args) {
        ApplicationContext springContext = new ClassPathXmlApplicationContext("classpath:spring/inmemory-user-manager.xml");

        AuthenticationManager authenticationManager = springContext.getBean(AuthenticationManager.class);

        // successful authentication
        Authentication loginToken1 = new UsernamePasswordAuthenticationToken("max", "rocks");
        Authentication user1 = authenticationManager.authenticate(loginToken1);
        System.out.println(user1.getName()); // prints "max"
        System.out.println(user1.getAuthorities()); // prints "[ROLE_ADMIN]"

        // failed authentication
        try {
            Authentication loginToken2 = new UsernamePasswordAuthenticationToken("max", "sucks");
            // the authentication fails because it's not true.
            authenticationManager.authenticate(loginToken2);
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage()); // prints "Bad credentials"
        }

    }

}
