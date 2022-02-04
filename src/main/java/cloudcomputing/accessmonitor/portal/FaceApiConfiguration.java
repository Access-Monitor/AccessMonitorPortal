package cloudcomputing.accessmonitor.portal;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static cloudcomputing.accessmonitor.portal.constants.FaceApiConstants.*;
import static cloudcomputing.accessmonitor.portal.constants.HttpConstants.*;

import java.net.URI;


@Configuration
public class FaceApiConfiguration {


    @Bean
    public void createPersonGroup(){

        CloseableHttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder(FACEAPI_ENDPOINT+ "/face/v1.0/persongroups/" + FACEAPI_PERSON_GROUP_NAME);


            URI uri = builder.build();
            HttpPut request = new HttpPut(uri);
            request.setHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON);
            request.setHeader(OCP_APIM_SUBSCRIPTION_KEY_HEADER, FACEAPI_SUBSCRIPTION_KEY);


            // Request body
            StringEntity reqEntity = new StringEntity("{\n" +
                    "    \"name\": \" "+ FACEAPI_PERSON_GROUP_NAME +" \",\n" +
                    "    \"userData\": \"gruppo di prova\",\n" +
                    "    \"recognitionModel\": \"recognition_03\"\n" +
                    "}");
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
                System.out.println(EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }


    }



}
