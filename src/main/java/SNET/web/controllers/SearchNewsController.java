package SNET.web.controllers;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import SNET.config.UserDetailsImpl;
import SNET.domain.dto.NewsDTO;
import SNET.domain.services.NewsDomainServices;
import SNET.web.form.CommentForm;
import SNET.web.form.NewNewsForm;

@RestController
public class SearchNewsController {
	
	@Autowired
    private MessageSource messageSource;
	
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
	public ResponseEntity<?> addNews(@Valid @ModelAttribute NewNewsForm form,
			BindingResult binding,
			ModelAndView modelAndView, Authentication auth) {
		
		if(binding.hasErrors()) {
			return new ResponseEntity<>(messageSource.getMessage("news.add.blank.text", null, Locale.ENGLISH), HttpStatus.BAD_REQUEST);
		}
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		newsService.addNewNews(form, userDetails.getUser());
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/news/update")
	public ResponseEntity<?> updateNews(@Valid @ModelAttribute NewNewsForm form,
			BindingResult binding,
			ModelAndView modelAndView, Authentication auth) {
		if(binding.hasErrors()) {
			return new ResponseEntity<>(messageSource.getMessage("news.add.blank.text", null, Locale.ENGLISH), HttpStatus.BAD_REQUEST);
		};
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		if (!newsService.updateNewNews(form, userDetails.getUser()))
			return new ResponseEntity<>(messageSource.getMessage("profile.news.update.author.error", null, Locale.ENGLISH), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/news/comment/add")
	public ResponseEntity<?> addComment(@Valid @ModelAttribute CommentForm form,
    		BindingResult binding,
    		HttpServletResponse response,
    		Authentication auth){
		
		if(binding.hasErrors()) {
			return new ResponseEntity<>(messageSource.getMessage("comment.add.blank.text", null, Locale.ENGLISH), HttpStatus.BAD_REQUEST);
		}
		
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		newsService.addComment(form, userDetails.getUser());
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/news/comment/update")
	public ResponseEntity<?> updateComment(@Valid @ModelAttribute CommentForm form,
    		BindingResult binding, Authentication auth){
		if(binding.hasErrors()) {
			return new ResponseEntity<>(messageSource.getMessage("comment.add.blank.text", null, Locale.ENGLISH), HttpStatus.BAD_REQUEST);
		}
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		if (!newsService.updateComment(form, userDetails.getUser())) 
			return new ResponseEntity<>(messageSource.getMessage("profile.news.update.author.error", null, Locale.ENGLISH), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}

