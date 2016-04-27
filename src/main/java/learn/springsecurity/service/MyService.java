package learn.springsecurity.service;

import org.springframework.security.access.annotation.Secured;


public class MyService {

    @Secured("ROLE_USER")
    public String getProjectStatus() {
        return "Green";
    }

    @Secured("ROLE_ADMIN")
    public int getProjectBudget() {
        return 1000000;
    }

}
