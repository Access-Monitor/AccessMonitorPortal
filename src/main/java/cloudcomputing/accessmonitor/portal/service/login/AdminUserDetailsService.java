package cloudcomputing.accessmonitor.portal.service.login;

import cloudcomputing.accessmonitor.portal.model.persistence.Admin;
import cloudcomputing.accessmonitor.portal.service.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<Admin> admins = repository.findByEmail(email);
        Admin admin = null;
        if (admins.size() == 0){
            throw new UsernameNotFoundException("User not found");
        }
        else {
            admin = admins.get(0);
        }

        return new AdminUserDetails(admin);

    }
}
