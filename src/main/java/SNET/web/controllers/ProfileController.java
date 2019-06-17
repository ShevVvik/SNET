package SNET.web.controllers;
import java.io.File;
import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import SNET.config.UserDetailsImpl;
import SNET.domain.entity.Role;
import SNET.domain.entity.User;
import SNET.domain.services.FriendListDomainServices;
import SNET.domain.services.NewsDomainServices;
import SNET.domain.services.UserDomainServices;
import SNET.web.form.UserEditForm;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@Controller
public class ProfileController {
	
	@Value("${project.manager.avatar.dir.path}")
    private String avatarDirPath;
	
	public static String BIG_AVATAR_POSTFIX = "_big_thumb.png";
	
	@Autowired
	private UserDomainServices userService;
	
	@Autowired
	private NewsDomainServices newsService;
	
	@Autowired
	private FriendListDomainServices friendsService;

	@GetMapping("/profile")
	public String profile(Model model) {
		
		UserDetailsImpl userDet = (UserDetailsImpl) SecurityContextHolder
		        .getContext()
		        .getAuthentication()
		        .getPrincipal();
		User user = userDet.getUser();
		return "redirect:/u/" + user.getId();
	}
	
	@GetMapping("/edit")
	public String registration(Model model, UserEditForm userForm, Authentication auth) {
		
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		
		BeanUtils.copyProperties(userDetails.getUser(), userForm);
		model.addAttribute("user", userDetails.getUser());
		model.addAttribute("userForm", userService.getCompleteUserEditForm(userDetails.getUser()));
		return "/user/edit";
	}
	
	@PostMapping("edit")
	public String registrationPost(Model model,@Valid @ModelAttribute("userForm") UserEditForm userForm,
				BindingResult binding, @RequestParam("files") MultipartFile[] files, Authentication auth) {
		
		
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		if(binding.hasErrors()) {
			model.addAttribute("user", userDetails.getUser());
			model.addAttribute("userForm", userForm);
			return "/user/edit";
		}

		if (files != null) {
		for (MultipartFile multipartFile : files) {
            String filePath =  avatarDirPath + File.separator + userForm.getLogin() + File.separator;
    
            if(! new File(filePath).exists()) {
                new File(filePath).mkdirs();
            }
            
            try {
                String orgName = multipartFile.getOriginalFilename();
                String fullFilePath = filePath + orgName;
        
                File dest = new File(fullFilePath);
                Thumbnails.of(dest).size(200, 200).crop(Positions.CENTER).toFile(new File(filePath + userForm.getLogin() + BIG_AVATAR_POSTFIX));
                multipartFile.transferTo(dest);
            } catch (IllegalStateException e) {
                System.out.println(e);
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
		}
		userService.updateUser(userForm, userDetails.getUser());
		return "redirect:/u/" + userDetails.getUser().getId();
	}
	
	@GetMapping("/{userId}/friendlist")
	public String friendlist(Model model, @PathVariable Long userId) {
		UserDetailsImpl userDet = (UserDetailsImpl) SecurityContextHolder
		        .getContext()
		        .getAuthentication()
		        .getPrincipal();
		User user = userDet.getUser();
		
		model.addAttribute("friends", friendsService.getActiveFriends(userId));
		model.addAttribute("user", userService.getById(userId));
		if (user.getId() == userId) {
			model.addAttribute("owner", true);
		} else {
			model.addAttribute("owner", false);
		}
		return "/user/friendlist";
	}
	
	@GetMapping("/u/{userId}")
	public String index(Model model, @PathVariable Long userId) {
		
		UserDetailsImpl userDet = (UserDetailsImpl) SecurityContextHolder
		        .getContext()
		        .getAuthentication()
		        .getPrincipal();
		User userX = userDet.getUser();
		User user = userService.getById(userId);

		model.addAttribute("user", user);
		model.addAttribute("userAut", userX);
		model.addAttribute("news", newsService.getNewsByAuthor(user.getId(), userX));
		model.addAttribute("friends", (friendsService.isFriendsRequest(userX, user)) ? true : false);
		model.addAttribute("otherUser", (user.equals(userX) || (userX.getHighLevelRole().equals(Role.ROLE_ADMIN))) ? false : true);
		model.addAttribute("createNews", (user.equals(userX)) ? false : true);
		
		return "/user/profile";
	}
	
	@GetMapping("/search")
	public String searchPage(Model model) {
		return "/user/search";
	}
}
