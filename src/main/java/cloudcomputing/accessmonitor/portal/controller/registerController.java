package cloudcomputing.accessmonitor.portal.controller;

import cloudcomputing.accessmonitor.portal.model.ajax.AdminRegistration;
import cloudcomputing.accessmonitor.portal.model.ajax.AjaxResponseBody;
import cloudcomputing.accessmonitor.portal.model.persistence.Admin;
import cloudcomputing.accessmonitor.portal.service.repo.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class registerController {

    @Autowired
    private AdminRepository repository;

    @RequestMapping(value = "/createNewAdmin", method = RequestMethod.GET)
    public String createNewAdminDisp() {


        return "registerNewAdmin";
    }


    @RequestMapping(value = "/addNewAdmin", method = RequestMethod.POST)
    public ResponseEntity<?> createNewAdmin (
            @Valid @RequestBody AdminRegistration registration, Errors errors) throws Exception {

        AjaxResponseBody result = new AjaxResponseBody();

        Admin admin = new Admin();
        admin.setEmailAddress(registration.getEmail());
        admin.setFirstName(registration.getFirstname());
        admin.setLastName(registration.getLastname());
        admin.setPassword(registration.getPassword());


        if (repository.findByEmail(registration.getEmail()).size() == 0){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(admin.getPassword());
            admin.setPassword(encodedPassword);
            repository.save(admin);
            result.setMsg("success");
        }
        else {
            result.setMsg("Email già utilizzata!");
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);

    }


}
