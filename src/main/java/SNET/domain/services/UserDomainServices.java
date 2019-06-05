package SNET.domain.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SNET.dao.UserRepository;
import SNET.domain.dto.CommentsDTO;
import SNET.domain.dto.NewsDTO;
import SNET.domain.dto.UserDTO;
import SNET.domain.entity.Comments;
import SNET.domain.entity.News;
import SNET.domain.entity.User;
import SNET.domain.entity.UserRole;
import SNET.web.form.UserRegistrationForm;

@Service
public class UserDomainServices {

	@Autowired
	public UserRepository userDao;

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

	
	public void createUserFromRegistrationForm(UserRegistrationForm userForm) {
		User u = new User();
		
		BeanUtils.copyProperties(userForm, u);
		u.setEnabled(true);
		
		userDao.save(u);
	}

	public List<UserDTO> searchUsertByPatternAsJson(String pattern, String parametr) {
		
		List<User> user = null;
		switch(parametr) {
		case "firstName": user = userDao.findAllByFirstNameContainingOrderByIdDesc(pattern);
		case "lastName" : user = userDao.findAllByLastNameContainingOrderByIdDesc(pattern);
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
}
