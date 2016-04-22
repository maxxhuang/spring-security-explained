package learn.springsecurity;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static util.Utils.assume;

/**
 * <img src="doc-files/AuthenticationManager.png" />
 */
public class O01_Abstract_Authentication_Flow {

    public static void main(String[] args) {
        // step1: gather request token from user
        Authentication token = assume("authentication token from user input");

        // step2: locate configured authenticationManager
        //        this is taken care of by Spring container
        AuthenticationManager authenticationManager = assume("A configured Spring bean");

        // step3: perform authentication
        Authentication userPrincipal = authenticationManager.authenticate(token);

        // step4: put populated Authentication in SecurityContext for later use (authorization)
        SecurityContextHolder.getContext().setAuthentication(userPrincipal);

    }

}
