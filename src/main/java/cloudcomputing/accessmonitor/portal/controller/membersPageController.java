package cloudcomputing.accessmonitor.portal.controller;


import cloudcomputing.accessmonitor.portal.model.persistence.Member;
import cloudcomputing.accessmonitor.portal.service.repo.MemberRepository;
import com.azure.cosmos.models.PartitionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

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

        int offset = page * 10;

        List<Member> members = repository.getUsersWithOffsetLimit(offset , 10);

        long numberOfMembers = repository.count();

        session.setAttribute("memberPage", members);
        session.setAttribute("numberOfMembers", numberOfMembers);
        session.setAttribute("currentPage", page);

        return "tableMemberPage";

    }

    @RequestMapping(value = "/removeMember", method = RequestMethod.GET)
    public String addNewMemberPage(
            Model model,
            HttpSession session,
            @RequestParam("id") String id
    ) {

        repository.deleteById(id, new PartitionKey(id));

        List<Member> members = (List<Member>) session.getAttribute("memberPage");
        members.remove(new Member(id, "", "", "", "", ""));
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


}
