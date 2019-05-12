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
@Table(name="news")
public class News implements Serializable {
	private static final long serialVersionUID = 7316344084865363418L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_news")
	private int id;
		
	@ManyToOne
    @JoinColumn(name="author_id", nullable=false)
 	private User author;
	
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	@Column(name="news_text", length=255, nullable=false)
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name="link_image", length=255, nullable=true)
 	private byte[] image;
	
	//ИСПРАВИТЬ КАК БУДЕТ ВОЗМОЖНОСТЬ
	//@Column(name="news_date", nullable=false)
	// private DATETAMETYPE_CHECK_ME news_date;
	
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
	
	
}
