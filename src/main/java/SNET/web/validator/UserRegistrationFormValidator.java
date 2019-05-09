package SNET.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import springweb.domain.services.UserDomainServices;
import springweb.web.form.UserRegistrationForm;

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

	}

}
