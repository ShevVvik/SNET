package SNET.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import SNET.config.UserDetailsImpl;
import SNET.domain.dto.NewsDTO;
import SNET.domain.services.NewsDomainServices;
import SNET.web.form.MessageForm;
import SNET.web.form.NewNewsForm;

@RestController
public class SearchNewsController {
	
	@Autowired
	private NewsDomainServices newsService;
	
	@RequestMapping(value="/news/filter", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<NewsDTO> newsFilter(@RequestParam("q") String pattern, 
    								@RequestParam("id") String id, ModelAndView modelAndView, 
    								Authentication auth) {
		
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
    	return newsService.searchNewsByPatternAsJson(HtmlUtils.htmlEscape(pattern), 
    												Long.parseLong(HtmlUtils.htmlEscape(id)), userDetails.getUser());
    }
	
	@RequestMapping(value="/news/add")
	public ResponseEntity<?> addNews(@ModelAttribute NewNewsForm form,
			ModelAndView modelAndView) {
		
		newsService.addNewNews(form);
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}

