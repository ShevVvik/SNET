package SNET.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import SNET.config.UserDetailsImpl;
import SNET.domain.entity.User;
import SNET.domain.services.FriendListDomainServices;

@Controller
public class FriendAcceptController {
	
	@Autowired
	private FriendListDomainServices friendsService;
	
	@GetMapping("/s/{token}")
	public String profile(Model model, @PathVariable String token) {
		friendsService.createFriendship(friendsService.getFriendsByToken(token));
		return "redirect:/friendlist";
	}
	
	
}
