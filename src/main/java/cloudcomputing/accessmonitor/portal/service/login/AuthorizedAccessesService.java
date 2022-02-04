package cloudcomputing.accessmonitor.portal.service.login;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobStorageException;
import org.apache.commons.codec.binary.Base64;

import static cloudcomputing.accessmonitor.portal.constants.BlobConstants.*;

public class AuthorizedAccessesService {

    private BlobServiceClient blobServiceClient;

    public String getImageFromBlobContent(String blob , String path , String nomeFile){

        BinaryData imageBytes;
        String base64Image;

        blobServiceClient = new BlobServiceClientBuilder().connectionString(BLOB_CONNECTION_STRING).buildClient();

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(blob);
        try {
            BlobClient blobClient = containerClient.getBlobClient(path + nomeFile);
            imageBytes = blobClient.downloadContent();
            base64Image = Base64.encodeBase64String(imageBytes.toBytes());
        }
        catch (BlobStorageException e ){
            base64Image  = null;
        }


        return base64Image;

    }


    public void saveMemberImageToBlob(String fileName, byte[] imageBytes) throws BlobStorageException {

        String localPath = "./images/";

        blobServiceClient = new BlobServiceClientBuilder().connectionString(BLOB_CONNECTION_STRING).buildClient();

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(BLOB_MEMBERS_CONTAINER_NAME);

        BlobClient blobClient = containerClient.getBlobClient(localPath + fileName);

        blobClient.upload(BinaryData.fromBytes(imageBytes));


    }

    public void removeMemberImageToBlob(String fileName) throws BlobStorageException {

        String localPath = "./images/";

        blobServiceClient = new BlobServiceClientBuilder().connectionString(BLOB_CONNECTION_STRING).buildClient();

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(BLOB_MEMBERS_CONTAINER_NAME);

        BlobClient blobClient = containerClient.getBlobClient(localPath + fileName);

        blobClient.delete();

    }



}
