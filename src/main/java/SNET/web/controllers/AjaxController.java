package SNET.web.controllers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import SNET.config.UserDetailsImpl;
import SNET.domain.entity.FriendList;
import SNET.domain.entity.Role;
import SNET.domain.services.FriendListDomainServices;
import SNET.domain.services.NewsDomainServices;
import SNET.domain.services.UserDomainServices;
import SNET.web.form.MessageForm;



@RestController
@RequestMapping("/ajax")
@Validated
public class AjaxController {

	@Autowired
	private FriendListDomainServices friendsService;
	
	@Autowired
	private UserDomainServices userService;
	
	@Autowired
	private NewsDomainServices newsService;

	@PostMapping("/addFriend")
    public ResponseEntity<String> addFriend(@RequestParam("q") String pattern, ModelAndView modelAndView, Authentication auth) {
    	
    	UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();		
		friendsService.addFriend(userDetails.getUser(), (long)Integer.parseInt(pattern));
    	
    	return new ResponseEntity<String>(HttpStatus.OK);
    }
	
	@PostMapping("/deleteNews")
    public ResponseEntity<String> deleteNews(@RequestParam("id") String idNews, ModelAndView modelAndView, Authentication auth) {
    	
    	UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
    	if (userDetails.getUser().getHighLevelRole() == Role.ROLE_ADMIN) {
    		newsService.deleteNews(Long.parseLong(idNews));
    	}
		
    	return new ResponseEntity<String>(HttpStatus.OK);
    }
	
	@PostMapping("/sendMessage")
    public ResponseEntity<String> addFriend(@Valid @ModelAttribute MessageForm form,
    		BindingResult binding,
    		HttpServletResponse response,
    		Model modelAndView, Authentication auth) throws IOException {
    	
		if(binding.hasErrors()) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
    	UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();	
    	System.out.println(form.getIdTo());
    	userService.sendMessage(form, userDetails.getUser());
    	return new ResponseEntity<String>(HttpStatus.OK);
    }
}