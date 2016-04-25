package learn.springsecurity;

import org.springframework.context.*;
import org.springframework.context.support.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * For web applications running on Java EE servers, Spring Security provides a filter-based
 * mechanism called Security Filter Chain to facilitate authentication and authorization.
 * <p>
 * Security Filter Chain provides common authentication & authorization methods that
 * can works out of the box with just a few settings.
 * <p>
 * As Security Filter Chain is a composite of many filter and other components. It is practically
 * created via Spring Namespace or Java Configuration. Following is an example of configuring a
 * filter chain:
 * <pre>
 *     &lt;security:http authentication-manager-ref="authenticationManager" use-expressions="false"&gt;
 *         &lt;security:intercept-url pattern="/**" access="IS_AUTHENTICATED_FULLY" /&gt;
 *         &lt;security:http-basic /&gt;
 *     &lt;/security:http&gt;
 * </pre>
 * which creates a chain of filters as below:
 * <ol>
 *     <li>SecurityContextPersistenceFilter</li>
 *     <li>WebAsyncManagerIntegrationFilter</li>
 *     <li>HeaderWriterFilter</li>
 *     <li>CsrfFilter</li>
 *     <li>BasicAuthenticationFilter</li>
 *     <li>RequestCacheAwareFilter</li>
 *     <li>SecurityContextHolderAwareRequestFilter</li>
 *     <li>AnonymousAuthenticationFilter</li>
 *     <li>SessionManagementFilter</li>
 *     <li>ExceptionTranslationFilter</li>
 *     <li>FilterSecurityInterceptor</li>
 * </ol>
 * Please refer to http://docs.spring.io/autorepo/docs/spring-security/current/reference/htmlsingle/#security-filter-chain
 * for detailed information.
 * <p>
 * The order of the filters is carefully designed so that Spring Security can orchestrate the filters to
 * <ul>
 *     <li>collect authentication information</li>
 *     <li>trigger authentication with configured AuthenticationManager (login)</li>
 *     <li>trigger authorization</li>
 *     <li>handle failed authentication</li>
 *     <li>handle failed authorization</li>
 *     <li>session management</li>
 *     <li>logout</li>
 * </ul>
 * to name a few.
 * <p>
 * If the default filters somehow cannot meet your requirement, it is also possible to hook
 * customized filters into Security Filter Chain. The order of filters always matters, so
 * it is crucial to place your customized filter in the right position. Please reference
 * http://docs.spring.io/autorepo/docs/spring-security/current/reference/htmlsingle/#ns-custom-filters
 * for the hint of filer positioning.
 * <p>
 * As far as request token collecting and authentication triggering is concerned, here is the interactions
 * among client (browser), Security Chain, and Authentication Manager:
 * <ol>
 *     <li>client attempts to access protected resource "/admin/user"</li>
 *     <li>
 *         {@link FilterSecurityInterceptor} detects client has no permission to access this resource
 *         and throws an exception.
 *         it triggers {@link AuthenticationEntryPoint} which sends back a 401 (UNAUTHORIZED) status code to client.
 *     </li>
 *     <li>
 *         {@link ExceptionTranslationFilter} sees an exception raised and launches authentication
 *         procedure via configured {@link BasicAuthenticationEntryPoint} which sends back a 401 (UNAUTHORIZED) status code to client.
 *     </li>
 *     <li>
 *         On receiving 401 response, client inputs username/password and makes another request with username/password
 *         embedded the information in "Authorization" header.
 *     </li>
 *     <li>
 *         {@link BasicAuthenticationFilter} intercepts this request, extracts username / password from
 *         request header (Authorization) and invoke {@link AuthenticationManager#authenticate(Authentication)}.
 *         BasicAuthenticationFilter places authentication Authentication in SecurityContext by calling
 *         <code>SecurityContextHolder.getContext().setAuthentication(authResult)</code>
 *     </li>
 *
 * </ol>
 * <p><p>
 * This section gives an example of complete authentication flow on a web application that
 * takes advantage of Security Filter Chain.
 * <p>
 * We bootstrap a web application on embedded Jetty server which reads resources/web/web.xml
 * as web descriptor.
 *
 */
public class O06_Authentication_Flow_with_Spring_Security_Filter_Chain {

    public static void main(String[] args) {

        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/jetty.xml");

    }

}
