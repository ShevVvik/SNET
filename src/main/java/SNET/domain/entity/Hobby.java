package SNET.domain.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hobby")
public class Hobby {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="nameHobby", length=320, nullable=false)
	private String nameHobby;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameHobby() {
		return nameHobby;
	}

	public void setNameHobby(String nameHobby) {
		this.nameHobby = nameHobby;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", nameHobby=" + nameHobby + "]";
    }
	
	@Override
    public int hashCode() {
        return Objects.hash(id, nameHobby);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj == this)
            return true;

        if (!(obj instanceof User))
        return false;

        Hobby hobby = (Hobby)obj;

        if (hobby.hashCode() == this.hashCode())
            return true;

        return false;
    }
	
}
