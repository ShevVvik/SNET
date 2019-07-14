package SNET.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.JoinColumn;


@Entity
@Table(name="user")
public class User implements Serializable {

	private static final long serialVersionUID = 6216344084865363418L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idUser")
	private Long id;

	@Column(name="email", length=320, nullable=false)
	private String email;
	
	@Column(name="firstName", length=64, nullable=false)
	private String firstName;

	@Column(name="lastName", length=64, nullable=false)
	private String lastName;

	@Column(name="login", length=64, nullable=false)
	private String login;
	
	@Column(name="password", length=64, nullable=false)
	private String password;

	@Column(name="enabled")
	private boolean enabled;

	@Column(name="token", length=32, nullable=false)
	private String token;

	@Column(name="city", length=32, nullable=true)
	private String city;

	@Column(name="education", length=32, nullable=true)
	private String education;
	
	@ManyToMany(fetch = FetchType.LAZY,
		    cascade = {
		        CascadeType.PERSIST,
		        CascadeType.MERGE
		    })
			@JoinTable(name = "user_hobby",
		    joinColumns = { @JoinColumn(name = "idUser") },
		    inverseJoinColumns = { @JoinColumn(name = "idHobby") })
	private Set<Hobby> userHobbies;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="user", orphanRemoval = true)
	private Set<UserRole> userRoles;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="author", orphanRemoval = true)
	private Set<News> news;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dateBirthday", nullable=false)
	@LastModifiedDate
	private Date dateBirthday;
	
	public User(Long id) {
		this.id = id;
	}
	
	public User() {	}
	
	public Date getDateBirthday() {
		return dateBirthday;
	}

	public void setDateBirthday(Date dateBirthday) {
		this.dateBirthday = dateBirthday;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<UserRole> getUserRoles() {
		if (userRoles == null) {
			userRoles = new HashSet<>();
		}
		return userRoles;
	}

	public void setUserRole(Set<UserRole> role) {
		this.userRoles = role;
	}
	
	public Set<Hobby> getUserHobbies() {
		if (userHobbies == null) {
			userHobbies = new HashSet<>();
		}
		return userHobbies;
	}
	
	public void setUserHobbies(Set<Hobby> hobby) {
		this.userHobbies = hobby;
	}
	
	public Set<News> getNews() {
		if (news == null) {
			news = new HashSet<>();
		}
		return news;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" 
				+ lastName + ", password=" + password + ", login=" + login + ", city=" + city + ", education=" + education + "]";
    }

	public String getHighLevelRole() {

	    List<String> allRoles = new ArrayList<>();
	    for (UserRole role : this.getUserRoles()) {
            allRoles.add(role.getRole());
        }

	    if (allRoles.contains(Role.ROLE_ADMIN)) {
	        return Role.ROLE_ADMIN;
	    } else {
	        return Role.ROLE_USER;
	    }
	}

	public List<String> getRolesList() {
	    List<String> list = new ArrayList<>();

	    for (UserRole role : this.getUserRoles()) {
            list.add(role.getRole());
        }

	    return list;
	}

	public List<Hobby> getHobbiesList() {
	    List<Hobby> list = new ArrayList<>();
	    if (userHobbies != null) {
	    	for (Hobby hobby : userHobbies) {
            	list.add(hobby);
        	}
	    };
	    return list;
	}
	
	public List<News> getNewsList() {
	    List<News> list = new ArrayList<>();
	    
	    for (News news : this.getNews()) {
            list.add(news);
        }

	    return list;
	}

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj == this)
            return true;

        if (!(obj instanceof User))
        return false;

        User user = (User)obj;

        if (user.hashCode() == this.hashCode())
            return true;

        return false;
    }
}