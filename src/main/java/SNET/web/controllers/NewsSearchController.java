package SNET.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import SNET.domain.dto.NewsDTO;
import SNET.domain.services.NewsDomainServices;

@RestController
public class NewsSearchController {
	
	@Autowired
	private NewsDomainServices newsService;
	
	@RequestMapping(value="/news/filter", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<NewsDTO> newsFilter(@RequestParam("q") String pattern, 
    								@RequestParam("id") String id, ModelAndView modelAndView) {
    	return newsService.searchNewsByPatternAsJson(HtmlUtils.htmlEscape(pattern), 
    												Long.parseLong(HtmlUtils.htmlEscape(id)));
    }
	
	@RequestMapping(value="/news/add", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void addNews(@RequestParam("newsText") String text, 
    					@RequestParam("id") String id, ModelAndView modelAndView) {
		System.out.println("TEST");
		newsService.addNewNews(text, Long.parseLong(id));
	}
}

