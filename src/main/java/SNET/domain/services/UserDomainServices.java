package SNET.domain.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import SNET.dao.UserHobbiesRepository;
import SNET.dao.UserRepository;
import SNET.domain.dto.UserDTO;
import SNET.domain.entity.Hobby;
import SNET.domain.entity.User;
import SNET.domain.entity.UserHobby;
import SNET.domain.entity.UserRole;
import SNET.web.form.MessageForm;
import SNET.web.form.UserEditForm;
import SNET.web.form.UserRegistrationForm;

@Service
public class UserDomainServices {

	@Autowired
    private MessageSource messageSource;
	
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

	public void createUserFromRegistrationForm(UserRegistrationForm userForm) {
		User u = new User();
		BeanUtils.copyProperties(userForm, u);
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		Date date = new Date();
		try {
			date = format.parse(userForm.getDateBirthday());
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		u.setDateBirthday(date);
		u.setEnabled(false);
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		u.setToken(UUID.randomUUID().toString());
		u.setUserHobbies(hobbyService.getAllHobbyListUser(userForm.getHobby()));		
		if (!StringUtils.isEmpty(u.getEmail())) {
	        String message = String.format(
	        		messageSource.getMessage("message.activate", null, Locale.ENGLISH),
	                u.getFirstName(),
	                u.getToken()
	        );
	       mailSender.send(u.getEmail(), "Activation code", message);
		}
		userDao.save(u);
	}
	
	public void updateUser(UserEditForm userForm, User user) {
		BeanUtils.copyProperties(userForm, user);
		user.setUserHobbies(hobbyService.getAllHobbyListUser(userForm.getHobby()));
		userDao.save(user);
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
		case "hobby": {
			Hobby hobby = hobbyService.getHobbyByName(pattern);
			List<UserHobby> listHobby = userHobbiesDao.findByHobby(hobby);
			for (UserHobby hob : listHobby) {
				user.add(hob.getUser());
			};
		}; break;
		};
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
					messageSource.getMessage("message.change", null, Locale.ENGLISH),
	                user.getFirstName(),
	                user.getToken()
	        );
			mailSender.send(user.getEmail(), "Password", message);
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
			user.setPassword(passwordEncoder.encode(password));
			user.setToken(null);
			userDao.save(user);
			return true;
		}
		return false;
	}

	public UserEditForm getCompleteUserEditForm(User user) {
		UserEditForm form = new UserEditForm();
		BeanUtils.copyProperties(user, form);
		String hobbyForm = "";
		for (Hobby hobby : user.getHobbiesList()) {
			hobbyForm += hobby.getNameHobby() + ", ";
		}
		if (hobbyForm != "") {
			StringBuffer sb = new StringBuffer(hobbyForm);
			sb.delete(sb.length()-2, sb.length()-1);
			hobbyForm = sb.toString();
		}
		form.setHobby(hobbyForm);
		return form;
	}
}
