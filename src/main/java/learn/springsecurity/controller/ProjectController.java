package learn.springsecurity.controller;

import learn.springsecurity.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private MyService myService;

    @RequestMapping(path = "/status", method = RequestMethod.GET)
    public @ResponseBody String getProjectStatus() {
        return this.myService.getProjectStatus();
    }

    @RequestMapping(path = "/budget", method = RequestMethod.GET)
    public @ResponseBody String getProjectBudget() {
        return String.valueOf(this.myService.getProjectBudget());
    }

}
