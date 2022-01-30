package cloudcomputing.accessmonitor.portal.controller;


import cloudcomputing.accessmonitor.portal.model.persistence.Member;
import cloudcomputing.accessmonitor.portal.model.persistence.UnauthorizedDetection;
import cloudcomputing.accessmonitor.portal.service.repo.UnauthorizedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UnauthorizedController {

    @Autowired
    UnauthorizedRepository repository;

    @RequestMapping(value = "/UnauthorizedAcc", method = RequestMethod.GET)
    public String displayAccesses(HttpSession session,
                                  @RequestParam("page") int page
                                ) {

        int offset = page * 10;

        List<UnauthorizedDetection> accesses = repository.getUnauthorizedDetectionLimit(offset , 10);

        long numberOfUnAccesses = repository.count();

        session.setAttribute("numberOfUnAccesses", numberOfUnAccesses);
        session.setAttribute("currentPage", page);
        session.setAttribute("un_accesses" , accesses);

        return "accesses";
    }

}
