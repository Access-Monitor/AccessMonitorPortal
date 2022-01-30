package cloudcomputing.accessmonitor.portal.service.repo;



import cloudcomputing.accessmonitor.portal.model.persistence.UnauthorizedDetection;
import com.azure.spring.data.cosmos.repository.CosmosRepository;

import com.azure.spring.data.cosmos.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

;import java.util.List;

@Repository
public interface UnauthorizedRepository extends CosmosRepository<UnauthorizedDetection, String> {

    @Query("select * from c offset @offset limit @limit")
    List<UnauthorizedDetection> getUnauthorizedDetectionLimit(@Param("offset") int offset, @Param("limit") int limit);

}
