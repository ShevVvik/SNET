package SNET.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import SNET.domain.dto.UserDTO;
import SNET.domain.services.UserDomainServices;

@RestController
public class SearchUserController {

	@Autowired
	private UserDomainServices userService;
	
	@RequestMapping(value="/search", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<UserDTO> newsFilter(@RequestParam("q") String pattern, 
    		@RequestParam("parametr") String parametr, 
    		ModelAndView modelAndView) {
    	return userService.searchUsertByPatternAsJson(HtmlUtils.htmlEscape(pattern), HtmlUtils.htmlEscape(parametr));
    }
}
