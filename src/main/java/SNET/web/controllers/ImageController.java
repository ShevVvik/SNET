package SNET.web.controllers;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class ImageController {

    public static String BIG_AVATAR_POSTFIX = "_big_thumb.png";

    @Value("C:\\Folder")
    private String avatarDirPath;
    
    @Value("C:\\Folder\\News")
    private String newsImageDirPath;

    @GetMapping(value="/avatar/big/{login}", produces=MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public FileSystemResource bigAvatar(ModelAndView modelAndView, @PathVariable String login) {
    	
        return this.getAvatar(login, BIG_AVATAR_POSTFIX);
    }
        
    private FileSystemResource getAvatar(String login, String postfix) {
        String avatarFileName = avatarDirPath + File.separator + login + File.separator + login + postfix;
        File f = new File(avatarFileName);
        if(f.exists() && !f.isDirectory()) {
            return new FileSystemResource(f);
        } 
        avatarFileName = avatarDirPath + File.separator + "noName.png";
        f = new File(avatarFileName);
        return new FileSystemResource(f);
    }
  
    @GetMapping(value="/news/image/{id}", produces=MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public FileSystemResource newsImage(ModelAndView modelAndView, @PathVariable String id, HttpServletResponse response) {
    	String avatarFileName = newsImageDirPath + File.separator + id + File.separator + id + ".png";
    	
        File f = new File(avatarFileName);
        if(f.exists() && !f.isDirectory()) {
            return new FileSystemResource(f);
        }
    	
    	return null;
    }
}