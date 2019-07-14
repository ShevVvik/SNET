package SNET.web.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import SNET.domain.services.UserDomainServices;
import SNET.web.form.ChangePaswordForm;

@Controller
public class ForgotPasswordController {
	
	@Autowired
	private UserDomainServices userService;
	
	@GetMapping("/video")
    public ResponseEntity<InputStreamResource> video(HttpServletRequest request)
            throws UnsupportedEncodingException, FileNotFoundException {
		
        File file = new File("C:\\Folder" + File.separator + "test.webm");
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-disposition", "attachment; filename=\"" + file.getName() + "\"");
        System.out.println("ok");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(new FileInputStream(file)));
    }
	
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
}
