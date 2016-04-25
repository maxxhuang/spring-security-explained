package learn.springsecurity;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class DemoController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public @ResponseBody String hello() {
        return "Hello World";
    }

}
