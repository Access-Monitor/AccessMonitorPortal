package cloudcomputing.accessmonitor.portal.controller;

import cloudcomputing.accessmonitor.portal.model.persistence.AuthorizedDetection;
import cloudcomputing.accessmonitor.portal.model.persistence.UnauthorizedDetection;
import cloudcomputing.accessmonitor.portal.service.repo.AdminRepository;
import cloudcomputing.accessmonitor.portal.service.repo.AuthorizedRepository;
import cloudcomputing.accessmonitor.portal.service.repo.UnauthorizedRepository;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

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

        long now = Timestamp.valueOf(LocalDateTime.now()).getTime();

        List<UnauthorizedDetection> un_detections = repositoryUnauthorized.getUnauthorizedDetectionByDetectionTimestampBetween(now - (43200000 * 5));
        JSONArray accesses = new JSONArray();
        for (UnauthorizedDetection un : un_detections
             ) {
            accesses.add(un.getDetectionTimestamp());
        }
        List<AuthorizedDetection> aut_detections = repositoryAuthorized.getUnauthorizedDetectionByDetectionTimestampBetween(now - (43200000 * 5));
        JSONArray aut_accesses = new JSONArray();
        for (AuthorizedDetection un : aut_detections
        ) {
            aut_accesses.add(un.getDetectionTimestamp());
        }

        model.addAttribute("time",now);
        model.addAttribute("last_twelve_un" , accesses);
        model.addAttribute("last_twelve_aut" , aut_accesses);
        if (totAccess != 0) {
            model.addAttribute("perc_violation", (numUnauthorized * 100) / totAccess);
        }
        else{
            model.addAttribute("perc_violation", 0);
        }

        return "index";

    }


}
