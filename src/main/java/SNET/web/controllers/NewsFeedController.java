package SNET.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import SNET.config.UserDetailsImpl;
import SNET.domain.services.NewsDomainServices;

@Controller
public class NewsFeedController {
	
	@Autowired
	private NewsDomainServices newsService;
	
	@GetMapping("/feed")
	public String getFeedPage(Model model, Authentication auth) {
		
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		model.addAttribute("user", userDetails.getUser());
		model.addAttribute("news", newsService.getAllFriendsNews(userDetails.getUser()));
		return "/user/feed";
	}
}
