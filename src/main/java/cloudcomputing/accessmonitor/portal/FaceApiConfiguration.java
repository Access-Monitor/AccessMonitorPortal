package cloudcomputing.accessmonitor.portal;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;


@Configuration
public class FaceApiConfiguration {


    @Bean
    public void createPersonGroup(){
/*
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder("https://riconoscimento.cognitiveservices.azure.com/face/v1.0/persongroups/gruppo1");


            URI uri = builder.build();
            HttpPut request = new HttpPut(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", "");


            // Request body
            StringEntity reqEntity = new StringEntity("{\n" +
                    "    \"name\": \"gruppo1\",\n" +
                    "    \"userData\": \"gruppo di prova\",\n" +
                    "    \"recognitionModel\": \"recognition_03\"\n" +
                    "}");
            request.setEntity(reqEntity);

            HttpResponse response = ((CloseableHttpClient) httpclient).execute(request);
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

*/
    }

}
