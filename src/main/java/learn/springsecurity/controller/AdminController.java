package learn.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static List<String> ALL_USER_NAMES =
            Collections.unmodifiableList(Arrays.asList("max", "eddie"));

    @Autowired
    private UserDetailsManager userDetailsManager;


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public @ResponseBody String getAllUsers() {
        return "<table>" +
               tableBodyOfAllUsers() +
                "</table>";
    }

    private String tableBodyOfAllUsers() {
        return ALL_USER_NAMES.stream()
                .map(this.userDetailsManager::loadUserByUsername)
                .map(AdminController::toHtmlRow)
                .collect(Collectors.joining());
    }

    private static String toHtmlRow(UserDetails user) {
        return "<tr>" +
                    "<td>" + user.getUsername() + "</td>" +
                    "<td>" + user.getAuthorities() + "</td>" +
                    "<td>" + user.getPassword() + "</td>" +
                "</tr>";
    }

}
