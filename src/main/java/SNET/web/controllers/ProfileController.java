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
import SNET.domain.services.NewsDomainServices;
import SNET.domain.services.UserDomainServices;



@Controller
public class ProfileController {

	@Autowired
	private UserDomainServices userService;
	
	@Autowired
	private NewsDomainServices newsService;
	
	@Autowired
	private FriendListDomainServices friendsService;
	
	@GetMapping("/profile")
	public String profile(Model model) {
		
		UserDetailsImpl userDet = (UserDetailsImpl) SecurityContextHolder
		        .getContext()
		        .getAuthentication()
		        .getPrincipal();
		User user = userDet.getUser();
		
		model.addAttribute("user", user);
		model.addAttribute("news", newsService.getNewsByAuthor(user.getId()));
		System.out.println(friendsService.getFriends(user.getId()).get(1).getToken());
		model.addAttribute("userFriends", friendsService.getFriends(user.getId()));
		return "/user/profile";
	}
	
	@GetMapping("/friendlist")
	public String friendlist(Model model) {
		
		UserDetailsImpl userDet = (UserDetailsImpl) SecurityContextHolder
		        .getContext()
		        .getAuthentication()
		        .getPrincipal();
		User user = userDet.getUser();
		
		model.addAttribute("userFriends", friendsService.getFriends(user.getId()));
		return "/user/friendlist";
	}
	
	@GetMapping("/u/{userId}")
	public String index(Model model, @PathVariable Long userId) {
		
		UserDetailsImpl userDet = (UserDetailsImpl) SecurityContextHolder
		        .getContext()
		        .getAuthentication()
		        .getPrincipal();
		User userX = userDet.getUser();
		User user = userService.getById(userId);
		
		if (userX.equals(user)) return "redirect:/profile";

		model.addAttribute("user", user);
		model.addAttribute("news", newsService.getNewsByAuthor(user.getId()));
		
		return "/user/profile";
	}
	
}
