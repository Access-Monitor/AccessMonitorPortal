package cloudcomputing.accessmonitor.portal.service;

import cloudcomputing.accessmonitor.portal.model.persistence.Admin;
import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdminRepository extends CosmosRepository<Admin, String> {

    @Query("select * from Admin a where a.emailAddress = @email")
    List<Admin> findByEmail(@Param("email") String email);


}
