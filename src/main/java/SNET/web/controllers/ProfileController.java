package SNET.web.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import SNET.config.UserDetailsImpl;
import SNET.domain.dto.NewsDTO;
import SNET.domain.entity.FriendList;
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
	/*
	@Autowired
	private CommentsDomainServices commentsService;
	*/
	@GetMapping("/profile")
	public String profile(Model model) {
		
		UserDetailsImpl userDet = (UserDetailsImpl) SecurityContextHolder
		        .getContext()
		        .getAuthentication()
		        .getPrincipal();
		User user = userDet.getUser();
		
		model.addAttribute("user", user);
		model.addAttribute("news", newsService.getNewsByAuthor(user.getId()));
		model.addAttribute("hobby", user.getHobbiesList());
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
		model.addAttribute("user", user);
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
		model.addAttribute("otherUser", true);
		
		return "/user/profile";
	}
	
}
