package learn.springsecurity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;

import static util.Utils.assume;

/**
 * The only built-in implementation of {@link AuthenticationManager} is
 * {@link ProviderManager}.
 * <p>
 * ProviderManager contains a list of {@link AuthenticationProvider} and
 * delegates the authenticating work to its AuthenticationProviders.
 * As such, AuthenticationProvider has the same method signature as AuthenticationManager:
 * {@link AuthenticationProvider#authenticate(Authentication)}
 * <p>
 * <img src="doc-files/AuthenticationManager_and_Impl.png" />
 *
 */
public class O02_AuthenticationManager_Implementation {

    public static void main(String[] args) {

        // configure ProviderManager with one or more AuthenticationProviders
        AuthenticationProvider authenticationProvider1 = assume("configured Spring bean");
        AuthenticationProvider authenticationProvider2 = assume("configured Spring bean");

        ProviderManager providerManager = new ProviderManager(
                Arrays.asList(authenticationProvider1, authenticationProvider2));

        Authentication request = new UsernamePasswordAuthenticationToken("username", "password");
        Authentication principal = providerManager.authenticate(request);

        SecurityContextHolder.getContext().setAuthentication(principal);
    }

}
