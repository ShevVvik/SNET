package SNET.web.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserRegistrationForm {

	@Email(message = "Email empty")
	@NotBlank
	private String email;
	
	
	@NotNull
	@NotBlank
	private String password;
	
	@NotNull
	private String passwordConfirm;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
		
		if(!this.password.equals(this.passwordConfirm)) {
			this.passwordConfirm = null;
		}
		
	}
	
	
	
}
