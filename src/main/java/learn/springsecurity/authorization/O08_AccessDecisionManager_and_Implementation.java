package learn.springsecurity.authorization;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.JoinPoint;
import org.springframework.security.access.*;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * {@link AccessDecisionManager} is the main component to decide whether or not an action can be taken on a resource.
 * Observing {@link AccessDecisionManager#decide(Authentication, Object, Collection)}, the access control decision is
 * made based on the information:
 * <ul>
 *     <li>Authentication: an {@link Authentication} representing the caller</li>
 *     <li>
 *         Object: the secured object. It could be
 *                 <ul>
 *                     <li>{@link FilterInvocation} containing {@link HttpServletRequest}, {@link HttpServletResponse} and {@link FilterChain}.</li>
 *                     <li>{@link MethodInvocation} or {@link JoinPoint} standing for a method call.</li>
 *                 </ul>
 *     </li>
 *     <li>
 *         Collection&lt;ConfigAttribute&gt;: the attributes associated with the secured object. Usually,
 *         the attributes represent the permissions required to access the secured object.
 *     </li>
 * </ul>
 * <p>
 * Much like AuthenticationManager, the implementations of AccessDecisionManager delegates the authorization work to
 * a list of {@link AccessDecisionVoter}.
 * <p>
 * {@link AbstractAccessDecisionManager} make the final decision based on the result of decision voters' checks
 * - the total number of pass (ACCESS_GRANTED), failure (ACCESS_DENIED) and unknown(ABSTAIN)
 * <p>
 * Three concrete implementations of AbstractAccessDecisionManager have different strategies viewing voters's
 * overall result as a success or a failure. See
 * http://docs.spring.io/autorepo/docs/spring-security/4.1.0.RC1/reference/htmlsingle/#authz-arch
 * for more detailed explanation.
 * <p>
 * <img src="doc-files/AccessDecisionManager_and_Impl.png" width="850" height="650" alt="AccessDecisionManager Class Diagram">
 */
public class O08_AccessDecisionManager_and_Implementation {

    public static void main(String[] args) {

        // a man with the role of manager
        Authentication manager = new UsernamePasswordAuthenticationToken("max", "xxxxxx",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_MANAGER")));


        // create an AccessDecisionManager with one RoleVoter
        List<AccessDecisionVoter<?>> voters = Collections.singletonList(new RoleVoter());
        AccessDecisionManager accessDecisionManager = new AffirmativeBased(voters);

        // granted
        ConfigAttribute requiredPermission1 = new SecurityConfig("ROLE_MANAGER");
        accessDecisionManager.decide(manager, null, Collections.singleton(requiredPermission1));

        // denied
        try {
            ConfigAttribute requiredPermission2 = new SecurityConfig("ROLE_DIRECTOR");
            accessDecisionManager.decide(manager, null, Collections.singleton(requiredPermission2));
        } catch (AccessDeniedException e) {
            System.out.println(e.getMessage()); // prints "Access is denied"
        }
    }

}
