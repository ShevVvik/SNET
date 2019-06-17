package SNET.domain.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import SNET.dao.UserHobbiesRepository;
import SNET.dao.UserRepository;
import SNET.domain.dto.CommentsDTO;
import SNET.domain.dto.NewsDTO;
import SNET.domain.dto.UserDTO;
import SNET.domain.entity.Comments;
import SNET.domain.entity.Hobby;
import SNET.domain.entity.News;
import SNET.domain.entity.User;
import SNET.domain.entity.UserHobby;
import SNET.domain.entity.UserRole;
import SNET.web.form.MessageForm;
import SNET.web.form.UserRegistrationForm;

@Service
public class UserDomainServices {

	@Autowired
	private UserRepository userDao;
	
	@Autowired
	private UserHobbiesRepository userHobbiesDao;
	
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
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		Date date = new Date();
		try {
			date = format.parse(userForm.getDateBirthday());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		u.setDateBirthday(date);
		Set<Hobby> hobbies = hobbyService.getAllHobbyByName(hobby);
		u.setDateBirthday(u.getDateBirthday());
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
	
	public void sendMessage(MessageForm form, User uFrom) {
		User u = this.getById(form.getIdTo());
		if (!StringUtils.isEmpty(u.getEmail())) {
	        mailSender.send(u.getEmail(), form.getSubject(), form.getText(), uFrom.getEmail());
		}
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
		
		List<User> user = new ArrayList<User>();
		switch(parametr) {
		case "name": user = userDao.findAllByFirstNameContainingOrderByIdDesc(pattern); break;
		case "surname" : user = userDao.findAllByLastNameContainingOrderByIdDesc(pattern); break;
		case "city"     : user = userDao.findAllByCityContainingOrderByIdDesc(pattern); break;
		case "education": user = userDao.findAllByEducationContainingOrderByIdDesc(pattern); break;
		case "email": user = userDao.findAllByEmailContainingOrderByIdDesc(pattern); break;
		case "interest": {
			Hobby hobby = hobbyService.getHobbyByName(pattern);
			List<UserHobby> listHobby = userHobbiesDao.findByHobby(hobby);
			for (UserHobby hob : listHobby) {
				user.add(hob.getUser());
			};
		}; break;
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
		System.out.println(login);
		System.out.println(user);
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
