package SNET.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_hobby")
public class UserHobby implements Serializable {

	private static final long serialVersionUID = 6216344084865363418L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@ManyToOne
    @JoinColumn(name="idUser", nullable=false)
 	private User user;

	@ManyToOne
    @JoinColumn(name="idHobby", nullable=false)
	private Hobby hobby;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Hobby getHobby() {
		return hobby;
	}

	public void setHobby(Hobby hobby) {
		this.hobby = hobby;
	}
	
	public static UserHobby createUserHobby(Hobby hobby) {
		UserHobby userHobby = new UserHobby();
		userHobby.setHobby(hobby);

		return userHobby;
	}

}