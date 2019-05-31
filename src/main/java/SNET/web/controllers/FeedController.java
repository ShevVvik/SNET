package SNET.web.controllers;
/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import SNET.config.UserDetailsImpl;
import SNET.domain.services.FriendListDomainServices;

@Controller
public class FeedController {

	@Autowired
	private FriendListDomainServices friendsService;
	
	@GetMapping("/feed")
	public String profile(Model model, Authentication auth) {
		
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		friendsService.getActiveFriends(userDetails.getUser().getId());
		model.addAttribute()
		
		return "redirect:/friendlist";
	}
	
}
*/