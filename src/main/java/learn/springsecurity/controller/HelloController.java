package learn.springsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/hello")
public class HelloController {

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String greet() {
        Authentication whoAreYou = SecurityContextHolder.getContext().getAuthentication();
        return "Hello, " + (whoAreYou == null ? "Nobody" : whoAreYou.getName());
    }

}
