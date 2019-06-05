package SNET.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	private Long id;
		
	@ManyToOne
    @JoinColumn(name="authorId", nullable=false)
 	private User author;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="news", orphanRemoval = true)
	private Set<Comments> comments;
	

	@Column(name="newsText", length=255, nullable=false)
	private String text;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="newsDate", nullable=false)
	@LastModifiedDate
	private Date newsDate;
	
	public User getAuthor() {
		return author;
	}

	public Date getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(Date newsDate) {
		this.newsDate = newsDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Comments> getComments() {
		return comments;
	}

	public void setComments(Set<Comments> comments) {
		this.comments = comments;
	}

	public void setAuthor(User author) {
		this.author = author;
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public List<Comments> getCommentsList() {
	    List<Comments> list = new ArrayList<>();
	    
	    for (Comments comments : this.getComments()) {
            list.add(comments);
            System.out.println("comments");
            System.out.println(comments);
        }

	    return list;
	}
	
/*
	@Column(name="image", length=255, nullable=true)
 	private byte[] image;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="newsDate", nullable=false)
	@LastModifiedDate
	private Date newsDate;
	
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}*//*
	@Column(name="forFriends", nullable=false)
	private boolean for_friends;

	public boolean isFor_friends() {
		return for_friends;
	}

	public void setFor_friends(boolean for_friends) {
		this.for_friends = for_friends;
	}*/
// ниже еще должно быть image и newsDate?
	
	
	 @Override
	    public int hashCode() {
	        return Objects.hash(id, author, text);
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
