package SNET.web.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import SNET.domain.services.UserDomainServices;
import SNET.web.form.ChangePaswordForm;
import SNET.web.form.UserRegistrationForm;

@Controller
public class ForgotPasswordController {
	
	@Autowired
	private UserDomainServices userService;
	
	@GetMapping("/p/{token}")
	public String getPasswordToken(Model model, @PathVariable String token, ChangePaswordForm passForm) {
		model.addAttribute("token", token);
		model.addAttribute("passForm", passForm);
		return "/forgotPassword";
	}
	
	
	@PostMapping("/changePassword")
	public String profile(Model model, @Valid @ModelAttribute("passForm") ChangePaswordForm passForm,
			BindingResult binding) {
		
		if(binding.hasErrors()) {
			model.addAttribute("passForm", passForm);
			model.addAttribute("token", passForm.getToken());
			return "forgotPassword";
		}
		
		userService.changePassword(passForm.getPassword(), passForm.getToken());
		return "redirect:/login";
	}
	
	@PostMapping("/ajax/forgotPassword")
    public String forgotPassword(@RequestParam("login") String login, ModelAndView modelAndView) {
		userService.forgotPassword(login);
    	return "Succes";
    }
}
