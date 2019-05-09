package SNET.web.controller;


import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {

	//@Autowired
	//private MessageSource message;
	
	@GetMapping("/")
	public String index() {
		
		//System.out.println(message.getMessage("home.title", null, locale));
		
		return "index";
	}
	
}
