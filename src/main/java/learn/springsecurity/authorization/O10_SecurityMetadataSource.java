package learn.springsecurity.authorization;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.web.FilterInvocation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Part of the authorization flow is finding the associate {@link ConfigAttribute}.
 * This is exactly the responsibility of {@link SecurityMetadataSource}.
 * <p>
 * Given a secured object ({@link FilterInvocation} or {@link MethodInvocation}),
 * {@link SecurityMetadataSource#getAttributes(Object)} tells you the answer of
 * what the associated ConfigAttributes are.
 * <p>
 * Normally we don't manually create SecurityMetadataSource components unless you have very
 * specialized requirements. SecurityMetadataSource is usually created implicitly during the
 * configuration of authorization.
 * <p>
 *
 * <img src="doc-files/SecurityMetadataSource_and_Impl.png" width="850" height="650" alt="SecurityMetadataSource Class Diagram">
 */
public class O10_SecurityMetadataSource {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/metadatasource.xml");

        getAllMetadataSources(applicationContext).stream().forEach(metastaSource -> {
            System.out.println(metastaSource.getClass().getSimpleName());
            System.out.println(metastaSource.getAllConfigAttributes());
            System.out.println();
        });
    }

    private static List<SecurityMetadataSource> getAllMetadataSources(ApplicationContext context) {

        return Arrays.stream(context.getBeanNamesForType(AbstractSecurityInterceptor.class))
                .map(securityInterceptorName -> {
                    AbstractSecurityInterceptor securityInterceptor =
                            (AbstractSecurityInterceptor) context.getBean(securityInterceptorName);
                    return securityInterceptor.obtainSecurityMetadataSource();
                })
                .collect(Collectors.toList());

    }

}
