package SNET.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import SNET.config.UserDetailsImpl;
import SNET.domain.entity.User;
import SNET.domain.services.UserDomainServices;



@Controller
public class ProfileController {

	@Autowired
	private UserDomainServices userService;
	
	@GetMapping("/profile")
	public String index(Model model) {
		
		UserDetailsImpl userDet = (UserDetailsImpl) SecurityContextHolder
		        .getContext()
		        .getAuthentication()
		        .getPrincipal();
		User user = userDet.getUser();
		
		model.addAttribute("user", user);
		
		return "/user/profile";
	}
	
}
