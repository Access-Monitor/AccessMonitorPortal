package cloudcomputing.accessmonitor.portal.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;

import static cloudcomputing.accessmonitor.portal.constants.FaceApiConstants.*;
import static cloudcomputing.accessmonitor.portal.constants.HttpConstants.OCP_APIM_SUBSCRIPTION_KEY_HEADER;

public class PersonGruopService {

    public void removeMemberFromPersonGroup(String id) {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            URIBuilder builder = new URIBuilder(FACEAPI_ENDPOINT + "/face/v1.0/persongroups/" + FACEAPI_PERSON_GROUP_NAME
                    + "/persons/" + id);

            URI uri = builder.build();
            HttpDelete request = new HttpDelete(uri);
            request.setHeader(OCP_APIM_SUBSCRIPTION_KEY_HEADER, FACEAPI_SUBSCRIPTION_KEY);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                System.out.println(EntityUtils.toString(entity));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

}