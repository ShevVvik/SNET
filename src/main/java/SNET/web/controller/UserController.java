package SNET.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import SNET.domain.services.UserDomainServices;



@Controller
public class UserController {

	@Autowired
	private UserDomainServices userService;
	
	@GetMapping("/user/list")
	public String index(Model model) {
		
		model.addAttribute("users", null);
		
		return "/user/list";
	}
	
}
