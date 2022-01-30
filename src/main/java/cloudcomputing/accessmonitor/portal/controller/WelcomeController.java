package cloudcomputing.accessmonitor.portal.controller;

import cloudcomputing.accessmonitor.portal.service.repo.AdminRepository;
import cloudcomputing.accessmonitor.portal.service.repo.AuthorizedRepository;
import cloudcomputing.accessmonitor.portal.service.repo.UnauthorizedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WelcomeController {

    @Autowired
    private UnauthorizedRepository repositoryUnauthorized;

    @Autowired
    private AuthorizedRepository repositoryAuthorized;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String rootPage() {

        return "redirect:/index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexPage(Model model) {

        long numUnauthorized= repositoryUnauthorized.count();
        model.addAttribute("un_access" ,numUnauthorized);

        long numAuthorized = repositoryAuthorized.count();
        model.addAttribute("aut_access" , numAuthorized);

        long totAccess = numAuthorized + numUnauthorized;
        model.addAttribute("tot_access" , totAccess);

        model.addAttribute("perc_violation", (numUnauthorized * 100)/totAccess);

        return "index";

    }

}
