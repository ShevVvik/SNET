package SNET.web.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import SNET.domain.services.UserDomainServices;
import SNET.web.form.UserRegistrationForm;



@Component
public class UserRegistrationFormValidator implements Validator {

	@Autowired
	private UserDomainServices userDomainService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UserRegistrationForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		UserRegistrationForm form = (UserRegistrationForm)target;
		
		if(userDomainService.isUserWithEmailExist(form.getEmail())) {
			errors.rejectValue("email", "form.user.email.exist", "User with email already exists");
		}
		
		if(userDomainService.isUserWithLoginExist(form.getLogin())) {
			errors.rejectValue("login", "form.user.email.exist", "User with email already exists");
		}

	}

}
