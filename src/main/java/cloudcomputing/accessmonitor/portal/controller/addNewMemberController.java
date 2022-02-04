package cloudcomputing.accessmonitor.portal.controller;


import cloudcomputing.accessmonitor.portal.model.persistence.Member;
import cloudcomputing.accessmonitor.portal.service.login.AuthorizedAccessesService;
import cloudcomputing.accessmonitor.portal.service.repo.MemberRepository;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static cloudcomputing.accessmonitor.portal.constants.FaceApiConstants.*;
import static cloudcomputing.accessmonitor.portal.constants.HttpConstants.*;


import java.io.IOException;
import java.net.URI;

import java.util.Arrays;

@Controller
public class addNewMemberController  {

    //private final PersistenceServiceCosmosDBimpl persistenceService = new PersistenceServiceCosmosDBimpl();

    @Autowired
    private MemberRepository repository;

    @RequestMapping(value = "/addNewMember", method = RequestMethod.GET)
    public String addNewMemberPage(Model model) {
        System.out.println("called index controller");
        model.addAttribute("oggetto", Arrays.asList("ua"));
        return "addNewMember";
    }

    @RequestMapping(value = "/creationNewMember", method = {RequestMethod.GET , RequestMethod.POST})
    public String createNewPerson (
            @RequestParam(name = "avatar-file") MultipartFile file,
            @RequestParam(name = "firstname") String firstName,
            @RequestParam(name = "lastname") String lastName,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "role") String role,
            @RequestParam(name = "phone") String phone) throws Exception {

        String personId;

        System.out.println("Sono stato chimamto da  " + firstName);
        personId = createNewPerson(firstName, lastName);
        addFaceToPerson(personId, file);
        trainPersonGroup();
        storeNewMember(personId, email, role, phone, firstName, lastName, file);

        return "addNewMember";

    }

    /*
    //Creare exception personalizzata
    private void storeNewMember(String personId, String email, String role, String phoneNumber, String firstName,  String lastName) throws Exception{

        Member member = new Member(email, personId, role, phoneNumber, firstName,  lastName);
        try {
            persistenceService.addNewMember(member);
        }
        catch (ConflictException e){
           throw new Exception("L'utente è stato già inserito");
        }

    }
*/

    private void storeNewMember(String personId, String email, String role, String phoneNumber, String firstName,  String lastName, MultipartFile image) throws Exception{

        byte[] bytes = new byte[0];
        try {
            bytes = image.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Member member = new Member(email, personId, role, phoneNumber, firstName,  lastName ,personId);
        repository.save(member);

        try {
            new AuthorizedAccessesService().saveMemberImageToBlob(personId, bytes);
        }
        catch (Exception e){
            repository.delete(member);
        }

    }

    private String createNewPerson(String firstName, String lastName) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        String personId = null;

        try
        {
            URIBuilder builder = new URIBuilder(FACEAPI_ENDPOINT + "/face/v1.0/persongroups/" + FACEAPI_PERSON_GROUP_NAME + "/persons");



            URI uri = builder.build();
            System.out.println("create new persone uri " + uri);
            HttpPost request = new HttpPost(uri);
            request.setHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON );
            request.setHeader(OCP_APIM_SUBSCRIPTION_KEY_HEADER, FACEAPI_SUBSCRIPTION_KEY );

            System.out.println("subscription: " + FACEAPI_SUBSCRIPTION_KEY);

            JSONObject obj=new JSONObject();
            obj.put("name", firstName + lastName);
            obj.put("userData", "Ho creato la persona" + firstName + " " + lastName);

            // Request body
            StringEntity reqEntity = new StringEntity( JSONValue.toJSONString(obj)  );
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();


            if (entity != null)
            {
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(EntityUtils.toString(entity));
                personId = (String) json.get("personId");
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        System.out.println("personId: " + personId);

        return personId;

    }

    private void addFaceToPerson(String personId, MultipartFile image){


        byte[] bytes = new byte[0];
        try {
            bytes = image.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder(FACEAPI_ENDPOINT + "/face/v1.0/persongroups/"+ FACEAPI_PERSON_GROUP_NAME
                    +"/persons/"+personId+"/persistedFaces");


            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader(CONTENT_TYPE_HEADER, APPLICATION_OCTET_STREAM);
            request.setHeader(OCP_APIM_SUBSCRIPTION_KEY_HEADER, FACEAPI_SUBSCRIPTION_KEY);


            ByteArrayEntity reqEntity = new ByteArrayEntity(bytes, ContentType.APPLICATION_OCTET_STREAM);
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

    private void trainPersonGroup(){

        CloseableHttpClient httpclient = HttpClients.createDefault();

        //https://accessmonitor-cognitive-services.cognitiveservices.azure.com/face/v1.0/persongroups/mainpersongroup/train
        try
        {
            URIBuilder builder = new URIBuilder(FACEAPI_ENDPOINT + "/face/v1.0/persongroups/"+ FACEAPI_PERSON_GROUP_NAME
                    +"/train");


            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader(OCP_APIM_SUBSCRIPTION_KEY_HEADER, FACEAPI_SUBSCRIPTION_KEY);

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
