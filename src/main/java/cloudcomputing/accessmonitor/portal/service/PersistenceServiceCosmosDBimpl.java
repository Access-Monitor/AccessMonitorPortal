package cloudcomputing.accessmonitor.portal.service;

import static cloudcomputing.accessmonitor.portal.constants.DatabaseConstants.*;

import cloudcomputing.accessmonitor.portal.model.persistence.Member;
import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.models.CosmosContainerProperties;
import com.azure.cosmos.models.CosmosContainerResponse;
import com.azure.cosmos.models.CosmosDatabaseResponse;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.util.CosmosPagedIterable;

public class PersistenceServiceCosmosDBimpl {

    private final CosmosContainer container;

    public PersistenceServiceCosmosDBimpl() {
        CosmosClient client = new CosmosClientBuilder().endpoint(COSMOSDB_ENDPOINT)
                .key(COSMOSDB_SUBSCRIPTION_KEY)
                .consistencyLevel(ConsistencyLevel.EVENTUAL)
                .buildClient();
        CosmosDatabaseResponse databaseResponse = client.createDatabaseIfNotExists(DATABASE_NAME);
        CosmosDatabase database = client.getDatabase(databaseResponse.getProperties().getId());
        CosmosContainerResponse containerResponse =
                database.createContainerIfNotExists(new CosmosContainerProperties(AUDIT_CONTAINER_NAME, "/personId"));
        container = database.getContainer(containerResponse.getProperties().getId());
    }


    public CosmosPagedIterable<Member> lastDetectionByPersonID(String personId) {
        return container.queryItems("SELECT * FROM c WHERE c.personId='" + personId + "' ORDER BY c.detectionTimestamp ASC",
                new CosmosQueryRequestOptions(), Member.class);
    }


    public void addNewMember(Member newMember) {
        container.createItem(newMember, new PartitionKey(newMember.getPersonId()), new CosmosItemRequestOptions());
    }

}