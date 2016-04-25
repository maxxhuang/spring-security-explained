package learn.springsecurity.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.stream.*;

@Controller
@RequestMapping("/info")
public class InfoController {

    @Autowired
    private ApplicationContext applicationContext;


    @RequestMapping(value = "/filterChain", method = RequestMethod.GET)
    public @ResponseBody String getFilterChains() {
        DefaultSecurityFilterChain filterChain =
                this.applicationContext.getBean(DefaultSecurityFilterChain.class);

        return filterChain.getFilters().stream()
                .map(f -> f.getClass().getSimpleName())
                .collect(Collectors.joining("<br/>")) +
                "<br/>" + SecurityContextHolder.getContext().getAuthentication();

    }

}
