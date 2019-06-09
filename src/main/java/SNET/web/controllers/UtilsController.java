package SNET.web.controllers;

import java.io.InputStream;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import SNET.config.UserDetailsImpl;
import SNET.domain.entity.Role;
import SNET.domain.services.FriendListDomainServices;
import SNET.domain.services.NewsDomainServices;
import SNET.domain.services.UserDomainServices;

@Controller
@RequestMapping("/utils")
public class UtilsController {


	@RequestMapping("/getNewsBlock")
    public String getNewsBlock(ModelAndView modelAndView) {
    	return "pressetNews";
    }
	
}