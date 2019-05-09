package SNET.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import springweb.domain.services.UserDomainServices;
import springweb.web.form.UserRegistrationForm;
import springweb.web.form.validator.UserRegistrationFormValidator;

@Controller
public class UserController {

	@Autowired
	private UserDomainServices userService;
	
	@Autowired
	private UserRegistrationFormValidator userValidator;
	
	@InitBinder("userForm")
	private void initBinder(WebDataBinder binder) {
		binder.addValidators(userValidator);
	}
	
	@GetMapping("/user/list")
	public String index(Model model) {
		
		model.addAttribute("users", userService.getList());
		
		return "/user/list";
	}
	
	@GetMapping("/user/registration")
	public String registration(Model model, UserRegistrationForm userForm) {
		
		model.addAttribute("userForm", userForm);
		
		return "user/registration";
	}

	@PostMapping("/user/registration")
	public String registrationPost(Model model, @Valid @ModelAttribute("userForm") UserRegistrationForm userForm,
				BindingResult binding) {
		
		if(binding.hasErrors()) {
			model.addAttribute("userForm", userForm);
			return "user/registration";
		}
		
		userService.createUserFromRegistrationForm(userForm);
		
		return "redirect:/";
	}

}
