package SNET.domain.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDTO {
	
	private Long id;
	private String email;
	private String firstName;
	private String lastName;
	private String login;
	private String city;
	private String education;
	
	public UserDTO() {}
	
	public UserDTO(Long id, String email, String firstName, String lastName, String login, String city,
			String education) {
		super();
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.city = city;
		this.education = education;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
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
	
}
