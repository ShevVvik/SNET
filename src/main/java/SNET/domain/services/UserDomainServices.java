package SNET.domain.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import SNET.dao.UserRepository;
import SNET.domain.dto.CommentsDTO;
import SNET.domain.dto.NewsDTO;
import SNET.domain.dto.UserDTO;
import SNET.domain.entity.Comments;
import SNET.domain.entity.Hobby;
import SNET.domain.entity.News;
import SNET.domain.entity.User;
import SNET.domain.entity.UserRole;
import SNET.web.form.UserRegistrationForm;

@Service
public class UserDomainServices {

	@Autowired
	private UserRepository userDao;
	
    @Autowired
    private MailSender mailSender;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HobbyDomainServices hobbyService;
    
	public List<User> getList() {
		return userDao.findAll();
	}

	public User getById(Long id) {
		Optional<User> userOptional = userDao.findById(id);

		if (userOptional.isPresent()) {
			return userOptional.get();
		}

		return null;
	}

	public User getByEmail(String email) {
        User user = userDao.findByEmail(email);
        return user;
	}

	public boolean hasRole(User u, String role) {

		if (u.getUserRoles() != null && u.getUserRoles().size() > 0) {
			for (UserRole userRole : u.getUserRoles()) {
				if (userRole.getRole().equals(role)) {
					return true;
				}
			}
		}

		return true;
	}

	
	public void createUserFromRegistrationForm(UserRegistrationForm userForm, List<String> hobby) {
		User u = new User();
		
		BeanUtils.copyProperties(userForm, u);
		Set<Hobby> hobbies = hobbyService.getAllHobbyByName(hobby);
		u.setUserHobbies(hobbies);
		u.setEnabled(false);
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		u.setToken(UUID.randomUUID().toString());
		
		if (!StringUtils.isEmpty(u.getEmail())) {
	        String message = String.format(
	                "Hello, %s! \n" +
	                		"Welcome to SNET. Please, visit next link and activated you profile: http://localhost:8080/activate/%s",
	                u.getFirstName(),
	                u.getToken()
	        );

	       // mailSender.send(u.getEmail(), "Activation code", message);
		}
		userDao.save(u);
	}
	
	public  boolean activateUser(String code) {
		User user = userDao.findByToken(code);

	    if (user == null) {
	        return false;
	    }

	    user.setToken(null);
	    user.setEnabled(true);
	    userDao.save(user);

	    return true;
	}

	public boolean isUserWithEmailExist(String email) {
		return userDao.countByEmail(email) != 0 ? true : false;
	}

	public boolean isUserWithLoginExist(String login) {
		return userDao.countByLogin(login) != 0 ? true : false;
	}
	
	public List<UserDTO> searchUsertByPatternAsJson(String pattern, String parametr) {
		
		List<User> user = null;
		switch(parametr) {
		case "firstName": user = userDao.findAllByFirstNameContainingOrderByIdDesc(pattern);
		case "lastName" : user = userDao.findAllByLastNameContainingOrderByIdDesc(pattern);
		case "city"     : user = userDao.findAllByCityContainingOrderByIdDesc(pattern);
		case "education": user = userDao.findAllByEducationContainingOrderByIdDesc(pattern);
		}
		
		List<UserDTO> userJson = null;
		
		if (user != null && user.size() > 0) {
			userJson = new ArrayList<>();
			
			for (User u : user) {
				UserDTO userDTO = new UserDTO();
				
				BeanUtils.copyProperties(u, userDTO);
				userJson.add(userDTO);
			}
		}
		
		return userJson;
	}
	
	public boolean forgotPassword(String login) {
		User user = userDao.findByLogin(login);
		
		if (user != null) {
			if(user.getToken() == null) {
				user.setToken(UUID.randomUUID().toString());
				userDao.save(user);
			}
			String message = String.format(
	                "Hello, %s! \n" +
	                		"Welcome to SNET. Please, visit next link and change your password: http://localhost:8080/p/%s",
	                user.getFirstName(),
	                user.getToken()
	        );
			//mailSender.send(user.getEmail(), "Password", message);
			return true;
		}
		
		return false;
	}
	
	public boolean changePassword(String password, String token) {
		User user = userDao.findByToken(token);
		if (user != null) {
			if (!user.isEnabled()) {
				user.setEnabled(true);
			}
			user.setPassword(password);
			user.setToken(null);
			userDao.save(user);
			return true;
		}
		return false;
	}
}
