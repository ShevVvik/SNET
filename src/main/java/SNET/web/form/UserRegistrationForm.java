package SNET.web.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class UserRegistrationForm {
	
	@Email
	@NotNull
	@NotBlank
	private String email;
	
	@NotNull
	@NotBlank
	private String login;
	
	@NotNull
	@NotBlank
	private String firstName;
	
	@NotNull
	@NotBlank
	private String lastName;
	
	@NotNull
	@NotBlank
	private String password;
	
	@NotNull
	@NotBlank
	private String dateBirthday;
	
	private String city;
	
	private String education;
	
	@NotNull
	private String passwordConfirm;

	private String hobby;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getDateBirthday1() {
		return dateBirthday;
	}
	public void setDateBirthday1(String dateBirthday) {
		this.dateBirthday = dateBirthday;
	}
	
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getDateBirthday() {
		return dateBirthday;
	}
	public void setDateBirthday(String dateBirthday) {
		this.dateBirthday = dateBirthday;
	}	
}