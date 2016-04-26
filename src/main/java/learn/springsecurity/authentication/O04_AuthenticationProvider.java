package learn.springsecurity.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collections;

/**
 * {@link AuthenticationProvider} only authenticates {@link Authentication} whose type it supports.
 * <p>
 * For those Authentications that AuthenticationProvider does not support, {@link AuthenticationManager}
 * passes them to next AuthenticationProvider.
 * <p>
 * The result could be either of the following
 * <ul>
 *     <li>
 *         Successful: one of the AuthenticationProvider authenticates the request, the resulting Authentication
 *         is returned immediately without consulting any other AuthenticationProvider
 *     </li>
 *
 *     <li>
 *         Failed: the request is not supported by any of the AuthenticationProvider or does not pass
 *         any AuthenticationProvider's authentication check. An {@link AuthenticationException} is
 *         thrown in this case.
 *     </li>
 * </ul>
 * <p>
 * Some built-in implementations of AuthenticationProviders and the corresponding supported Authentications:<p>
 * <img src="doc-files/AuthenticationProvider_Impl.png" />
 */
public class O04_AuthenticationProvider {

    public static void main(String[] args) {

        AuthenticationManager authenticationManager = new ProviderManager(
                Arrays.asList(new MyAuthProvider1(), new MyAuthProvider2()));


        Authentication request1 = new MyAuthentication1();
        authenticationManager.authenticate(request1); // prints "authenticating in MyAuthProvider1"

        Authentication request2 = new MyAuthentication2();
        authenticationManager.authenticate(request2); // prints "authenticating in MyAuthProvider2"

        Authentication request3 = new MyAuthentication3();
        try {
            authenticationManager.authenticate(request3);
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage()); // prints "No AuthenticationProvider found for ..."
        }

    }

    static class MyAuthProvider1 implements AuthenticationProvider {

        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            System.out.println("authenticating in MyAuthProvider1");

            // always accept request authentication of supported type
            return authentication;
        }

        public boolean supports(Class<?> authentication) {
            return authentication == MyAuthentication1.class;
        }
    }

    static class MyAuthProvider2 implements AuthenticationProvider {

        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            System.out.println("authenticating in MyAuthProvider2");

            // always accept request authentication of supported type
            return authentication;
        }

        public boolean supports(Class<?> authentication) {
            return authentication == MyAuthentication2.class;
        }
    }


    ///////////////////////////////////////////////////////////////////////////


    static class MyAuthentication1 extends AbstractAuthenticationToken {

        public MyAuthentication1() {super(Collections.<GrantedAuthority>emptyList());
        }

        public Object getCredentials() { return "secret1"; }

        public Object getPrincipal() { return "name1"; }
    }

    static class MyAuthentication2 extends AbstractAuthenticationToken {

        public MyAuthentication2() {super(Collections.<GrantedAuthority>emptyList());
        }

        public Object getCredentials() { return "secret2"; }

        public Object getPrincipal() { return "name2"; }
    }

    static class MyAuthentication3 extends AbstractAuthenticationToken {

        public MyAuthentication3() {super(Collections.<GrantedAuthority>emptyList()); }

        public Object getCredentials() { return "secret3"; }

        public Object getPrincipal() { return "name3"; }
    }

}
