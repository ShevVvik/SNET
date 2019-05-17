package SNET.domain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name="news")
public class News implements Serializable {
	private static final long serialVersionUID = 7316344084865363418L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
		
	@ManyToOne
    @JoinColumn(name="authorId", nullable=false)
 	private User author;
	
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	@Column(name="newsText", length=255, nullable=false)
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
/*
	@Column(name="link_image", length=255, nullable=true)
 	private byte[] image;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="newsDate", nullable=false)
	@LastModifiedDate
	private Date newsDate;
	*/
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	@Column(name="for_friends", nullable=false)
	private boolean for_friends;

	public boolean isFor_friends() {
		return for_friends;
	}

	public void setFor_friends(boolean for_friends) {
		this.for_friends = for_friends;
	}
// ниже еще должно быть image и newsDate?
	 @Override
	    public int hashCode() {
	        return Objects.hash(id, author, text, for_friends);
	 }
	 @Override
	    public boolean equals(Object obj) {
	        if (obj == null)
	            return false;

	        if (obj == this)
	            return true;

	        if (!(obj instanceof News))
	        return false;

	        News news = (News)obj;

	        if (news.hashCode() == this.hashCode())
	            return true;

	        return false;
	    }
}
