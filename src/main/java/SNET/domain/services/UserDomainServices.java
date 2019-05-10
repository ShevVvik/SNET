package SNET.domain.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SNET.dao.UserRepository;
import SNET.domain.entity.User;
import SNET.domain.entity.UserRole;

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

}
