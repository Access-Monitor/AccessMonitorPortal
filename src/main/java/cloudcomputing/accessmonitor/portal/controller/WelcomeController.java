package cloudcomputing.accessmonitor.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController {

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcomePage() {
        System.out.println("called welcome controller");
        return "welcome";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage() {
        System.out.println("called index controller");
        return "index";
    }
}
