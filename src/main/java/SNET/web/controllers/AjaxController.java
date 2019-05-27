package SNET.web.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import SNET.config.UserDetailsImpl;
import SNET.domain.entity.FriendList;
import SNET.domain.services.FriendListDomainServices;
import SNET.domain.services.UserDomainServices;



@RestController
@RequestMapping("/ajax")
public class AjaxController {

	@Autowired
	private FriendListDomainServices friendsService;
	
	@Autowired
	private UserDomainServices userService;

	@PostMapping("/addFriend")
    public String userFilter(@RequestParam("q") String pattern, ModelAndView modelAndView, Authentication auth) {
    	
    	UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		FriendList newFriend = new FriendList();
		newFriend.setUser1(userDetails.getUser());
		newFriend.setUser2(userService.getById((long)Integer.parseInt(pattern)));
		newFriend.setFriendship(false);
		newFriend.setToken(UUID.randomUUID().toString());
		
		friendsService.addFriend(newFriend);
    	
    	return "Succes";
    }
	
	
}