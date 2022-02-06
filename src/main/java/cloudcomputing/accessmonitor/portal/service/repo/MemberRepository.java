package cloudcomputing.accessmonitor.portal.service.repo;

import cloudcomputing.accessmonitor.portal.model.persistence.Admin;
import cloudcomputing.accessmonitor.portal.model.persistence.Member;
import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MemberRepository extends CosmosRepository<Member, String> {

    @Query("select * from c offset @offset limit @limit")
    List<Member> getUsersWithOffsetLimit(@Param("offset") int offset, @Param("limit") int limit);
/*
    @Query("select * from Member a where a.id = id")
    List<Member> findByEmail(@Param("id") String id);
*/

}
