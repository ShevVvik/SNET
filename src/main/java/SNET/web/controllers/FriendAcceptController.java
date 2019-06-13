package SNET.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import SNET.config.UserDetailsImpl;
import SNET.domain.dto.FriendDTO;
import SNET.domain.dto.UserDTO;
import SNET.domain.entity.Role;
import SNET.domain.entity.User;
import SNET.domain.services.FriendListDomainServices;

@RestController
public class FriendAcceptController {
	
	@Autowired
	private FriendListDomainServices friendsService;
	
	@GetMapping("/s/{token}")
	public String profile(Model model, @PathVariable String token) {
		friendsService.createFriendship(friendsService.getFriendsByToken(token));
		return "redirect:/friendlist";
	}

	@RequestMapping(value="/friendList/request", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<FriendDTO> getRequestFriends(@RequestParam("id") String idUser, 
    		ModelAndView modelAndView) {
    	return friendsService.getRequestFriends(Long.parseLong(idUser));
    }
	
	@RequestMapping(value="/friendList/active", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<FriendDTO> getActiveFriends(@RequestParam("id") String idUser, 
    		ModelAndView modelAndView) {
    	return friendsService.getActiveFriends(Long.parseLong(idUser));
    }
}
