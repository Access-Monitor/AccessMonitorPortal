package cloudcomputing.accessmonitor.portal.controller;

import cloudcomputing.accessmonitor.portal.model.persistence.Admin;
import cloudcomputing.accessmonitor.portal.service.AdminRepository;
import cloudcomputing.accessmonitor.portal.service.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Array;
import java.util.Arrays;

@Controller
public class WelcomeController {

    @Autowired
    private AdminRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String rootPage() {

        return "redirect:/index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexPage() {

        return "index";
    }

}
