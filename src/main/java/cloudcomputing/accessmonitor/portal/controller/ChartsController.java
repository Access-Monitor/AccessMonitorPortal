package cloudcomputing.accessmonitor.portal.controller;

import cloudcomputing.accessmonitor.portal.model.ajax.AjaxResponseBody;
import cloudcomputing.accessmonitor.portal.model.persistence.UnauthorizedDetection;
import cloudcomputing.accessmonitor.portal.service.repo.AuthorizedRepository;
import cloudcomputing.accessmonitor.portal.service.repo.UnauthorizedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

public class ChartsController {

    @Autowired
    private UnauthorizedRepository repositoryUnauthorized;

    @Autowired
    private AuthorizedRepository repositoryAuthorized;

    @RequestMapping(value = "/GetLastHoursAccesses", method = RequestMethod.GET)
    public ResponseEntity<?> verifyMail (
            @RequestParam(name = "time") int time) throws Exception {

        AjaxResponseBody result = new AjaxResponseBody();

        repositoryUnauthorized.getUnauthorizedDetectionByDetectionTimestampBetween(time);


        return ResponseEntity.ok(result);

    }


}
