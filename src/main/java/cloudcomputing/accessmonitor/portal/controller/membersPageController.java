package cloudcomputing.accessmonitor.portal.controller;


import cloudcomputing.accessmonitor.portal.model.persistence.Member;
import cloudcomputing.accessmonitor.portal.service.MemberRepository;
import com.azure.core.http.rest.Page;
import com.azure.cosmos.models.PartitionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class membersPageController {

    @Autowired
    private MemberRepository repository;

    @RequestMapping(value = "/displayAllMembers", method = RequestMethod.GET)
    public String addNewMemberPage(
            Model model,
            HttpSession session,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {

        int offset = page * size;

        List<Member> members = repository.getUsersWithOffsetLimit(offset , size);

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
