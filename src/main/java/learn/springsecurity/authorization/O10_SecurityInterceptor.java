package learn.springsecurity.authorization;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.JoinPoint;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;
import org.springframework.security.access.intercept.aspectj.AspectJMethodSecurityInterceptor;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import util.JettyServer;

/**
 * {@link AbstractSecurityInterceptor} is the component that drives the authorization process. It has
 * two types of implementations. Each type of implementations provides its own of "Secure Object".
 * <p>
 * For filter-based implementation, {@link FilterSecurityInterceptor}, it's {@link FilterInvocation}.
 * For method-based implementation, {@link MethodSecurityInterceptor}, {@link AspectJMethodSecurityInterceptor},
 * they are {@link MethodInvocation} and {@link JoinPoint} respectively.
 * <p>
 * <img src="doc-files/SecurityInterceptor.png" />
 */
public class O10_SecurityInterceptor {

    public static void main(String[] args) {
        // import "authn-and-authz.xml" in spring-security-web.xml
        JettyServer.run();
    }

}
