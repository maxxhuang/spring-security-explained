package learn.springsecurity.authorization;

import org.springframework.security.access.*;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

import static util.Utils.assume;

/**
 * As with AuthenticationManager to authentication flow, AccessDecisionManager alone is not able to
 * walk you through the whole authorization flow. Here is the complete authorization flow.
 * <ol>
 *     <li>Identify the {@link ConfigAttribute} associated with the secured object specified by the request.</li>
 *     <li>Invoke {@link AccessDecisionManager#decide(Authentication, Object, Collection)} for authorization check</li>
 * </ol>
 *NOTE: Many details regarding authorization are omitted here.
 * Check out {@link AbstractSecurityInterceptor#beforeInvocation(Object)} to see what Spring Security does
 * for authorization.
 */
public class O09_Missing_Parts_of_Authorization_Flow {

    public static void main(String[] args) {

        AuthorizationRunner authorizationRunner = createAuthorizationRunner();

        Authentication manager = new UsernamePasswordAuthenticationToken("manager1", "xxxxxx",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_MANAGER")));

        Authentication engineer = new UsernamePasswordAuthenticationToken("engineer1", "xxxxxx",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ENGINEER")));

        Object securedObject = assume("a secured object representing the operation of managing project");

        try {
            authorizationRunner.doAuthz(manager, securedObject);
            System.out.println(manager.getName() + " can manager project");
        } catch (AccessDeniedException e) {
            System.out.println(manager.getName() + " is not allowed to manage project");
        }

        try {
            authorizationRunner.doAuthz(engineer, securedObject);
            System.out.println(engineer.getName() + " can manager project");
        } catch (AccessDeniedException  e) {
            System.out.println(engineer.getName() + " is not allowed to manager project");
        }

    }

    private static AuthorizationRunner createAuthorizationRunner() {
        AccessDecisionVoter voter = new RoleVoter();

        AccessDecisionManager accessDecisionManager = new AffirmativeBased(
                Collections.singletonList(voter));

        return new AuthorizationRunner(accessDecisionManager);
    }

    public static class AuthorizationRunner {

        private AccessDecisionManager accessDecisionManager;

        public AuthorizationRunner(AccessDecisionManager accessDecisionManager) {
            this.accessDecisionManager = accessDecisionManager;
        }

        public void doAuthz(Authentication user, Object securedObject) {
            Collection<ConfigAttribute> requiredAuthorities = getConfigAttributes(securedObject);
            this.accessDecisionManager.decide(user, securedObject, requiredAuthorities);
        }

        public Collection<ConfigAttribute> getConfigAttributes(Object securedObject) {
            return Collections.singleton(new SecurityConfig("ROLE_MANAGER"));
        }
    }
}
