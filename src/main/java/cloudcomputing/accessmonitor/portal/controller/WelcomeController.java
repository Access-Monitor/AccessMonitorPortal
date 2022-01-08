package cloudcomputing.accessmonitor.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Array;
import java.util.Arrays;

@Controller
public class WelcomeController {

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcomePage() {
        System.out.println("called welcome controller");
        return "/WEB-INF/views/" + "welcome.jsp";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage() {
        System.out.println("called index controller");
        return "/WEB-INF/views/" + "index.jsp";
    }

    @RequestMapping(value = "/ciao", method = RequestMethod.GET)
    public String ciaoPage(Model model) {
        System.out.println("called index controller");
        model.addAttribute("oggetto", Arrays.asList("ua", "bell", "stu", "thymeleaf"));
        return "ciao";
    }

    @RequestMapping(value = "/H", method = {RequestMethod.GET, RequestMethod.POST})
    public String hPage(Model model) {
        System.out.println("called index controller");
        model.addAttribute("oggetto", Arrays.asList("ua"));
        return "H";
    }

    @RequestMapping(value = "/provaht", method = RequestMethod.GET)
    public String provaPage(Model model) {
        System.out.println("called index controller");
        model.addAttribute("oggetto", Arrays.asList("ua"));
        return "/prova/provaht";
    }





}
