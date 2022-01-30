package cloudcomputing.accessmonitor.portal.service.repo;

import cloudcomputing.accessmonitor.portal.model.persistence.AuthorizedDetection;
import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorizedRepository extends CosmosRepository<AuthorizedDetection, String> {

    @Query("select * from c offset @offset limit @limit")
    List<AuthorizedDetection> getAuthorizedDetectionLimit(@Param("offset") int offset, @Param("limit") int limit);

}