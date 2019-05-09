package SNET.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Role implements Serializable {
	private static final long serialVersionUID = 6216344026865363418L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idRole")
	private Long idRole;

	@Column(name="nameRole", length=320, nullable=false)
	private String nameRole;

	public Long getIdRole() {
		return idRole;
	}

	public void setIdRole(Long idRole) {
		this.idRole = idRole;
	}

	public String getNameRole() {
		return nameRole;
	}

	public void setNameRole(String nameRole) {
		this.nameRole = nameRole;
	}
	
}
