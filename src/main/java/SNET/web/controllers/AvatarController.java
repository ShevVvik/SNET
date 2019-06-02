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
@RequestMapping("/avatar")
public class AvatarController {

    public static String BIG_AVATAR_POSTFIX = "_big_thumb.png";
    public static String SMALL_AVATAR_POSTFIX = "_small_thumb.png";

    @Value("C:\\Folder")
    private String avatarDirPath;

    @GetMapping(value="/big/{userId}", produces=MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public FileSystemResource bigAvatar(ModelAndView modelAndView, @PathVariable Long userId) {
        return this.getAvatar(userId, BIG_AVATAR_POSTFIX);
    }
    
    
    @GetMapping(value="/small/{userId}", produces=MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public FileSystemResource smallAvatar(ModelAndView modelAndView, @PathVariable Long userId, HttpServletResponse response) {
        return this.getAvatar(userId, SMALL_AVATAR_POSTFIX);
    }
        
    private FileSystemResource getAvatar(Long id, String postfix) {
        String avatarFileName = avatarDirPath + File.separator + id + File.separator + id + postfix;
    
        File f = new File(avatarFileName);
        if(f.exists() && !f.isDirectory()) {
            return new FileSystemResource(f);
        }
    
        return null;
    }
}