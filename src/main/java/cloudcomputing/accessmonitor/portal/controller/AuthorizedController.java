package cloudcomputing.accessmonitor.portal.controller;

import cloudcomputing.accessmonitor.portal.model.excpetions.WrongParameterException;
import cloudcomputing.accessmonitor.portal.model.persistence.AuthorizedDetection;
import cloudcomputing.accessmonitor.portal.service.repo.AuthorizedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AuthorizedController {

    @Autowired
    AuthorizedRepository repository;

    @RequestMapping(value = "/AuthorizedAcc", method = RequestMethod.GET)
    public String displayAccesses(HttpSession session,
                                  @RequestParam("page") int page
    ) {

        if(page<0){
            throw new WrongParameterException("Page Number not valid");
        }

        int offset = page * 10;

        List<AuthorizedDetection> accesses = repository.getAuthorizedDetectionLimit(offset, 10);

        long numberOfAccesses = repository.count();

        session.setAttribute("numberOfAccesses", numberOfAccesses);
        session.setAttribute("currentPage", page);
        session.setAttribute("at_accesses" , accesses);

        return "authorizedAccesses";
    }

}
