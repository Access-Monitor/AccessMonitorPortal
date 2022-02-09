package cloudcomputing.accessmonitor.portal.controller;


import cloudcomputing.accessmonitor.portal.model.excpetions.WrongParameterException;
import cloudcomputing.accessmonitor.portal.model.persistence.Member;
import cloudcomputing.accessmonitor.portal.service.login.AuthorizedAccessesService;
import cloudcomputing.accessmonitor.portal.service.repo.MemberRepository;
import com.azure.cosmos.models.PartitionKey;
import com.azure.storage.blob.models.BlobStorageException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.List;

import static cloudcomputing.accessmonitor.portal.constants.FaceApiConstants.*;
import static cloudcomputing.accessmonitor.portal.constants.HttpConstants.OCP_APIM_SUBSCRIPTION_KEY_HEADER;

@Controller
public class membersPageController {

    @Autowired
    private MemberRepository repository;

    @RequestMapping(value = "/displayAllMembers", method = RequestMethod.GET)
    public String addNewMemberPage(
            Model model,
            HttpSession session,
            @RequestParam("page") int page
    ) {

        if(page<0){
            throw new WrongParameterException("Page Number not valid");
        }

        int offset = page * 10;

        List<Member> members = repository.getUsersWithOffsetLimit(offset , 10);

        long numberOfMembers = repository.count();

        session.setAttribute("memberPage", members);
        session.setAttribute("numberOfMembers", numberOfMembers);
        session.setAttribute("currentPage", page);

        return "tableMemberPage";

    }

    @RequestMapping(value = "/removeMember", method = {RequestMethod.GET , RequestMethod.POST})
    public String removeMember(
            Model model,
            HttpSession session,
            @RequestParam("id") String id,
            @RequestParam("personId") String personId
    ) {

        repository.deleteById(id, new PartitionKey(id));

        removeMemberFromPersonGroup(personId);

        trainPersonGroup();

        try {
            new AuthorizedAccessesService().removeMemberImageToBlob(personId);
        }
        catch (BlobStorageException e){
            //ops qualcosa è andato storto
        }

        List<Member> members = (List<Member>) session.getAttribute("memberPage");
        members.remove(new Member(id, "", "", "", "", "" ,null));
        session.setAttribute("memberPage", members);

        long numberOfMembers = (long) session.getAttribute("numberOfMembers");
        numberOfMembers--;
        session.setAttribute("numberOfMembers", numberOfMembers );

        int page = (int) session.getAttribute("currentPage");

        if(page*10>=numberOfMembers) {
            page--;
            members = repository.getUsersWithOffsetLimit(page * 10 , 10);
            session.setAttribute("memberPage", members);
        }

        session.setAttribute("currentPage", page);

        return "tableMemberPage";

    }

    public void removeMemberFromPersonGroup(String id) {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder(FACEAPI_ENDPOINT + "/face/v1.0/persongroups/"+ FACEAPI_PERSON_GROUP_NAME
                    +"/persons/" + id);

            URI uri = builder.build();
            HttpDelete request = new HttpDelete(uri);
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
