package SNET.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import SNET.domain.dto.UserDTO;
import SNET.domain.entity.Hobby;
import SNET.domain.entity.User;
import SNET.web.form.UserRegistrationForm;

public class ConverterUserDTO {

	public static UserDTO convertUserToUserDTO(User user) {
		return new UserDTO(
				user.getId(),
				user.getEmail(),
				user.getFirstName(),
				user.getLastName(),
				user.getLogin(),
				user.getCity(),
				user.getEducation());
	}
	
	public static User convertUserFormToUser(UserRegistrationForm userForm, Set<Hobby> hobby) {
		User u = new User();
		BeanUtils.copyProperties(userForm, u);
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		Date date = new Date();
		try {
			date = format.parse(userForm.getDateBirthday());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		u.setDateBirthday(date);
		u.setEnabled(false);
		u.setUserHobbies(hobby);
		return null;
	}
}

