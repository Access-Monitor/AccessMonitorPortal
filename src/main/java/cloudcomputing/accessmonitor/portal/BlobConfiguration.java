package cloudcomputing.accessmonitor.portal;


import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobContainerItem;
import com.azure.storage.blob.models.BlobStorageException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static cloudcomputing.accessmonitor.portal.constants.BlobConstants.BLOB_CONNECTION_STRING;
import static cloudcomputing.accessmonitor.portal.constants.BlobConstants.BLOB_MEMBERS_CONTAINER_NAME;

@Configuration
public class BlobConfiguration {


    @Bean
    public void createContainers(){

        BlobServiceClient blobServiceClient;

        blobServiceClient = new BlobServiceClientBuilder().connectionString(BLOB_CONNECTION_STRING).buildClient();

        try {
            BlobContainerClient containerClient = blobServiceClient.createBlobContainer("membersauthorized");
        }
        catch (BlobStorageException e){
            //Blob container already exists
        }


    }

}
