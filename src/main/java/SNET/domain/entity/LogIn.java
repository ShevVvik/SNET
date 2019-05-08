package SNET.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="autorizations")
public class LogIn {

	private static final long serialVersionUID = 6216344084865363418L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_user")
	private Long id;
	
	@Column(name="login", length=320, nullable=false)
	private String login;

	@Column(name="password", length=64, nullable=true)
	private String password;
	
}
