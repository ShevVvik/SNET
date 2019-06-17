package SNET.web.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import SNET.domain.services.HobbyDomainServices;
import SNET.domain.services.UserDomainServices;
import SNET.web.form.UserRegistrationForm;
import SNET.web.validators.UserRegistrationFormValidator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;


@Controller
public class RegistrationController {
	
	@Value("C:\\Folder")
    private String avatarDirPath;
	
	public static String BIG_AVATAR_POSTFIX = "_big_thumb.png";
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private UserDomainServices userService;
	
	@Autowired
	private HobbyDomainServices hobbyService;
	
	@Autowired
	private UserRegistrationFormValidator userValidator;
	
	@InitBinder("userForm")
	private void initBinder(WebDataBinder binder) {
		binder.addValidators(userValidator);
	}
	
	@GetMapping("registration")
	public String registration(Model model, UserRegistrationForm userForm) {
		
		model.addAttribute("userForm", userForm);
		model.addAttribute("allHobby", hobbyService.getAllHobby());
		return "registration";
	}

	@PostMapping("registration")
	public String registrationPost(Model model, @Valid @ModelAttribute("userForm") UserRegistrationForm userForm,
				BindingResult binding, @RequestParam("files") MultipartFile[] files) {
		
		
		if(binding.hasErrors()) {
			model.addAttribute("userForm", userForm);
			model.addAttribute("allHobby", hobbyService.getAllHobby());
			return "registration";
		}

		if (files != null) {
		for (MultipartFile multipartFile : files) {
            String filePath = avatarDirPath + File.separator + userForm.getLogin() + File.separator;
    
            if(! new File(filePath).exists()) {
                new File(filePath).mkdirs();
            }
            
            try {
                FileUtils.cleanDirectory(new File(filePath));
        
                String orgName = multipartFile.getOriginalFilename();
                String fullFilePath = filePath + orgName;
        
                File dest = new File(fullFilePath);
                Thumbnails.of(dest).size(200, 200).crop(Positions.CENTER).toFile(new File(filePath + userForm.getLogin() + BIG_AVATAR_POSTFIX));
                multipartFile.transferTo(dest);
                
            } catch (IllegalStateException e) {
                System.out.println(e);
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
		}
		userService.createUserFromRegistrationForm(userForm);
		
		return "redirect:/";
	}
	  
	@GetMapping("/activate/{code}")
	public String activate(Model model, @PathVariable String code) {
		boolean isActivated = userService.activateUser(code);

	    if (isActivated) {
	    	model.addAttribute("message", messageSource.getMessage("registration.activate.succses", null, Locale.ENGLISH));
	    } else {
	        model.addAttribute("message", messageSource.getMessage("registration.activate.fail", null, Locale.ENGLISH));
	    }

	    return "login";
	}
}
