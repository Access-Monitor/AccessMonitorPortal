package cloudcomputing.accessmonitor.portal.controller;


import cloudcomputing.accessmonitor.portal.model.excpetions.NoFaceException;
import cloudcomputing.accessmonitor.portal.model.ajax.AjaxResponseBody;
import cloudcomputing.accessmonitor.portal.model.excpetions.WrongParameterException;
import cloudcomputing.accessmonitor.portal.model.persistence.Member;
import cloudcomputing.accessmonitor.portal.service.login.AuthorizedAccessesService;
import cloudcomputing.accessmonitor.portal.service.repo.MemberRepository;
import cloudcomputing.accessmonitor.portal.service.PersonGruopService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static cloudcomputing.accessmonitor.portal.constants.FaceApiConstants.*;
import static cloudcomputing.accessmonitor.portal.constants.HttpConstants.*;


import java.io.IOException;
import java.net.URI;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Locale;

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


        if (!(firstName.length() >= 3 && firstName.matches("^[a-zA-Z]+$")))
        {
            throw new WrongParameterException("Firstname not valid");
        }

        if (!(lastName.length() >= 3 && lastName.matches("^[a-zA-Z]+$")))
        {
            throw new WrongParameterException("Lastname not valid");
        }

        if (!(phone.length() == 10 && phone.matches("^[0-9]+$")))
        {
            throw new WrongParameterException("Telefono non valido.");
        }

        if (!(role.length() >= 3 && role.matches("^[a-zA-Z]+$")))
        {
            throw new WrongParameterException("Role not valid");
        }

        if (!(email.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w+)+$"))) {
            throw new WrongParameterException("Email not valid");
        }


        String personId;
        personId = createNewPerson(firstName, lastName);
        addFaceToPerson(personId, file);
        trainPersonGroup();
        storeNewMember(personId, email.toLowerCase(Locale.ROOT), role, phone, firstName, lastName, file);


        return "addNewMember";

    }


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


            JSONObject obj=new JSONObject();
            obj.put("name", firstName + " " + lastName);
            obj.put("userData", "New person created: " + firstName + " " + lastName);

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

    private void addFaceToPerson(String personId, MultipartFile image) throws URISyntaxException,IOException,NoFaceException{


        byte[] bytes = new byte[0];
        try {
            bytes = image.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();


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

                if( response.getStatusLine().getStatusCode() != 200){
                    new PersonGruopService().removeMemberFromPersonGroup(personId);
                    throw new NoFaceException("Ops add an image with a face!");
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


    @RequestMapping(value = "/VerificaEmail", method = RequestMethod.GET)
    public ResponseEntity<?> verifyMail (
            @RequestParam(name = "email") String email) throws Exception {

        AjaxResponseBody result = new AjaxResponseBody();


        if (repository.findById(email.toLowerCase(Locale.ROOT)).isEmpty()){
            result.setMsg("<ok/>");
        }
        else {
            result.setMsg("Email già utilizzata!");
            return ResponseEntity.ok().body(result);
        }

        return ResponseEntity.ok(result);

    }

    @ExceptionHandler({NoFaceException.class})
    public String databaseError(Exception e, Model model) {

        model.addAttribute("exception" , e);

        return "exception";
    }




}
