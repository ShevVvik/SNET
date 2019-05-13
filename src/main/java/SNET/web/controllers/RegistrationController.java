package SNET.web.controllers;

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

import SNET.domain.services.UserDomainServices;
import SNET.web.form.UserRegistrationForm;
import SNET.web.validators.UserRegistrationFormValidator;




@Controller
public class RegistrationController {

	@Autowired
	private UserDomainServices userService;
	
	@Autowired
	private UserRegistrationFormValidator userValidator;
	
	@InitBinder("userForm")
	private void initBinder(WebDataBinder binder) {
		binder.addValidators(userValidator);
	}
	
	@GetMapping("registration")
	public String registration(Model model, UserRegistrationForm userForm) {
		
		model.addAttribute("userForm", userForm);
		
		return "registration";
	}

	@PostMapping("registration")
	public String registrationPost(Model model, @Valid @ModelAttribute("userForm") UserRegistrationForm userForm,
				BindingResult binding) {
		
		if(binding.hasErrors()) {
			model.addAttribute("userForm", userForm);
			return "login";
		}
		
		userService.createUserFromRegistrationForm(userForm);
		
		return "redirect:/";
	}
}
