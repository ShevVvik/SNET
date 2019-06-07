package SNET.web.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ChangePaswordForm {

	@NotNull
	@NotBlank
	private String password;
	
	@NotNull
	@NotBlank
	private String passwordConfirm;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@NotNull
	@NotBlank
	private String token;

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
